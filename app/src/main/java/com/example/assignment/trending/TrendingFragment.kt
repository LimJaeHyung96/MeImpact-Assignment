package com.example.assignment.trending

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.assignment.R
import com.example.assignment.databinding.FragmentTrendingGifBinding

class TrendingFragment : Fragment(R.layout.fragment_trending_gif) {

    private var binding: FragmentTrendingGifBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentTrendingGifBinding = FragmentTrendingGifBinding.bind(view)
        binding = fragmentTrendingGifBinding
    }
}