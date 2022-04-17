package com.example.assignment.favorite

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.assignment.R
import com.example.assignment.database.AppDatabase
import com.example.assignment.databinding.FragmentFavoriteBinding
import com.example.assignment.model.FavoriteGIFModel
import com.example.assignment.trending.TrendingAdapter

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private var binding: FragmentFavoriteBinding? = null

    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db =
            Room.databaseBuilder(requireContext(), AppDatabase::class.java, "favoriteGIFDB").build()

        favoriteAdapter = FavoriteAdapter(itemClicked = { favoriteGIFModel ->
            showFavoriteImageBigger(favoriteGIFModel, db)
        })

        getFavoriteGIF(db)

        val fragmentFavoriteBinding = FragmentFavoriteBinding.bind(view)
        binding = fragmentFavoriteBinding

        fragmentFavoriteBinding.favoriteRecyclerView.layoutManager = GridLayoutManager(context, 2)
        fragmentFavoriteBinding.favoriteRecyclerView.adapter = favoriteAdapter
    }

    private fun showFavoriteImageBigger(
        favoriteGIFModel: FavoriteGIFModel,
        db: AppDatabase
    ) {
        binding?.let { fragmentFavoriteBinding ->
            fragmentFavoriteBinding.showBigImageLayout.isVisible = true
            fragmentFavoriteBinding.cancelButton.setOnClickListener {
                fragmentFavoriteBinding.showBigImageLayout.isVisible = false
            }
            fragmentFavoriteBinding.deleteButton.setOnClickListener {
                showDeleteDialog(favoriteGIFModel, db)
                fragmentFavoriteBinding.showBigImageLayout.isVisible = false
            }
            Glide.with(fragmentFavoriteBinding.bigImageView)
                .load(favoriteGIFModel.url)
                .into(fragmentFavoriteBinding.bigImageView)
        }
    }

    private fun getFavoriteGIF(db: AppDatabase) {
        Thread(Runnable {
            val favoriteGIFModel = db.favoriteGIFModelDao().getAll()
            Log.d("Favorite", "$favoriteGIFModel")

            activity?.runOnUiThread {
                favoriteAdapter.submitList(favoriteGIFModel)
            }
        }).start()
    }

    private fun showDeleteDialog(
        favoriteGIFModel: FavoriteGIFModel,
        db: AppDatabase
    ) {
        AlertDialog.Builder(requireContext())
            .setTitle("알림")
            .setMessage("이 사진을 Favorite에서 삭제하시겠습니까?")
            .setNegativeButton("아니요") { _, _ -> }
            .setPositiveButton("네") { _, _ ->
                Log.d("Check", "Dialog")
                Thread(Runnable {
                    Log.d("Check", favoriteGIFModel.id)
                    db.favoriteGIFModelDao().deleteItem(favoriteGIFModel.id)
                    val favoriteGIF = db.favoriteGIFModelDao().getAll()
                    activity?.runOnUiThread {
                        favoriteAdapter.submitList(favoriteGIF)
                        favoriteAdapter.notifyDataSetChanged()
                    }
                }).start()
            }
            .create()
            .show()
    }

    override fun onResume() {
        super.onResume()
        favoriteAdapter.notifyDataSetChanged()
    }
}