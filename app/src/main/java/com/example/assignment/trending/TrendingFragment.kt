package com.example.assignment.trending

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.assignment.MainActivity
import com.example.assignment.R
import com.example.assignment.database.AppDatabase
import com.example.assignment.databinding.FragmentTrendingGifBinding
import com.example.assignment.dto.GIFDto
import com.example.assignment.key.APIKey.Companion.GIPHY_API_KEY
import com.example.assignment.service.GIFService
import com.example.assignment.state.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TrendingFragment() : Fragment(R.layout.fragment_trending_gif) {
    private var binding: FragmentTrendingGifBinding? = null
    lateinit var trendingAdapter: TrendingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentTrendingGifBinding = FragmentTrendingGifBinding.bind(view)
        binding = fragmentTrendingGifBinding

        Log.d("Context",context.toString())

        val db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "favoriteGIFDB").build()

        trendingAdapter = TrendingAdapter(db)

        fragmentTrendingGifBinding.progressBar.isVisible = true

        fragmentTrendingGifBinding.trendingRecyclerView.layoutManager =
            GridLayoutManager(context, 2)
        fragmentTrendingGifBinding.trendingRecyclerView.adapter = trendingAdapter

        getTrendingGIF()
    }

    private fun getTrendingGIF() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.giphy.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GIFService::class.java).also {
            it.getTrendingGIFList(GIPHY_API_KEY)
                .enqueue(object : Callback<GIFDto> {
                    override fun onResponse(call: Call<GIFDto>, response: Response<GIFDto>) {
                        if (response.isSuccessful.not()) {
                            Log.d("MainActivity", "사진을 받아오지 못함")
                            return
                        }

                        response.body()?.let { gifDto ->
                            trendingAdapter.submitList(gifDto.data)
                            binding?.let {  fragmentBinding ->
                                fragmentBinding.progressBar.isVisible = false
                            }
                            Log.d("MainActivity", gifDto.toString())
                        }
                    }

                    override fun onFailure(call: Call<GIFDto>, t: Throwable) {
                        Log.d("MainActivity", t.message!!.toString())
                    }
                })
        }
    }

    override fun onResume() {
        super.onResume()
        trendingAdapter.notifyDataSetChanged()
    }
}