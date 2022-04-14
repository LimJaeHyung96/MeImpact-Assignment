package com.example.assignment.trending

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.databinding.ItemTrendingBinding
import com.example.assignment.model.GIFModel

class TrendingAdapter() : ListAdapter<GIFModel, TrendingAdapter.ViewHolder>(diffUtil) {
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