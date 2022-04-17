package com.example.assignment.favorite

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.databinding.ItemFavoriteBinding
import com.example.assignment.model.FavoriteGIFModel

class FavoriteAdapter(val itemClicked: (FavoriteGIFModel) -> Unit) : ListAdapter<FavoriteGIFModel, FavoriteAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteGIFModel: FavoriteGIFModel) {
            Log.d("Favorite","${favoriteGIFModel.url} & ${favoriteGIFModel.id}")
            if (favoriteGIFModel.url.isNotEmpty()) {
                Glide.with(binding.gifImageView)
                    .load(favoriteGIFModel.url)
                    .into(binding.gifImageView)
                Log.d("Favorite","사진은 잘 가져옴")
            } else {
                Log.d("Favorite", "사진을 가져오지 못했습니다")
            }

            binding.root.setOnClickListener {
                itemClicked(favoriteGIFModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFavoriteBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<FavoriteGIFModel>() {
            override fun areItemsTheSame(oldItem: FavoriteGIFModel, newItem: FavoriteGIFModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteGIFModel, newItem: FavoriteGIFModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}