package com.example.assignment.trending

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.database.AppDatabase
import com.example.assignment.databinding.ItemTrendingBinding
import com.example.assignment.model.FavoriteGIFModel
import com.example.assignment.model.GIFModel

class TrendingAdapter(val db: AppDatabase) : ListAdapter<GIFModel, TrendingAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemTrendingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gifModel: GIFModel) {
            if (gifModel.url.isNotEmpty()) {
                Glide.with(binding.gifImageView)
                    .load(gifModel.images.original.url)
                    .into(binding.gifImageView)
                Log.d("Trending","사진은 잘 가져옴")
            } else {
                Log.d("Trending", "사진을 가져오지 못했습니다")
            }

            val list = FavoriteGIFModel(
                id = gifModel.id,
                url = gifModel.images.original.url,
                like = "like",
                uid = System.currentTimeMillis().toInt()
            )

            binding.favoriteButton.setOnClickListener {
                if(db.favoriteGIFModelDao().getId(gifModel.id) != gifModel.id){
                    //Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
                    // todo 쓰레드 만들어서 쓰레드에서 데이터베이스에 저장
                    db.favoriteGIFModelDao().insertGIF(list)
                    Toast.makeText(itemView.context, "추가!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTrendingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<GIFModel>() {
            override fun areItemsTheSame(oldItem: GIFModel, newItem: GIFModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GIFModel, newItem: GIFModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}