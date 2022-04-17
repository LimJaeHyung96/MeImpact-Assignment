package com.example.assignment.trending

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.database.AppDatabase
import com.example.assignment.databinding.ItemTrendingBinding
import com.example.assignment.model.FavoriteGIFModel
import com.example.assignment.model.GIFModel

class TrendingAdapter(val db: AppDatabase) : ListAdapter<GIFModel, TrendingAdapter.ViewHolder>(diffUtil) {

    val handler = Handler(Looper.getMainLooper())

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

            favoriteButtonToggle(gifModel.id)

            binding.touchGIF.setOnClickListener {
                makeFavoriteGIFModel(gifModel)
            }
        }

        private fun favoriteButtonToggle(gifId: String) {
            Thread(Runnable {
                if(db.favoriteGIFModelDao().getId(gifId) == gifId){
                    handler.post(Runnable {
                        binding.likeButton.isVisible = true
                        binding.dislikeButton.isVisible = false
                    })
                } else {
                    handler.post(Runnable {
                        binding.likeButton.isVisible = false
                        binding.dislikeButton.isVisible = true
                    })
                }
            }).start()
        }

        private fun makeFavoriteGIFModel(gifModel: GIFModel) {
            val list = FavoriteGIFModel(
                id = gifModel.id,
                url = gifModel.images.original.url,
                uid = System.currentTimeMillis().toInt(),
                like = "like"
            )

            Thread(Runnable {
                if(db.favoriteGIFModelDao().getId(gifModel.id) != gifModel.id){
                    db.favoriteGIFModelDao().insertGIF(list)
                    handler.post(Runnable {
                        Toast.makeText(itemView.context, "이 사진을 favorite에 추가했습니다!!", Toast.LENGTH_SHORT).show()
                        notifyItemChanged(adapterPosition)
                    })
                } else {
                    db.favoriteGIFModelDao().deleteItem(gifModel.id)
                    handler.post(Runnable {
                        Toast.makeText(itemView.context, "이 사진을 favorite에서 삭제했습니다!!", Toast.LENGTH_SHORT).show()
                        notifyItemChanged(adapterPosition)
                    })
                }
            }).start()
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