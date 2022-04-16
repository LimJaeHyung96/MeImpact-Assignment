package com.example.assignment.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.assignment.R
import com.example.assignment.database.AppDatabase
import com.example.assignment.databinding.FragmentFavoriteBinding
import com.example.assignment.trending.TrendingAdapter

class FavoriteFragment: Fragment(R.layout.fragment_favorite) {

    private var binding: FragmentFavoriteBinding? = null
    private val favoriteAdapter = FavoriteAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentFavoriteBinding = FragmentFavoriteBinding.bind(view)
        binding = fragmentFavoriteBinding

        fragmentFavoriteBinding.favoriteRecyclerView.layoutManager =
            GridLayoutManager(context, 3)
        fragmentFavoriteBinding.favoriteRecyclerView.adapter = favoriteAdapter
    }

    override fun onResume() {
        super.onResume()
        favoriteAdapter.notifyDataSetChanged()
    }
}