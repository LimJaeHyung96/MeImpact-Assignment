package com.example.assignment.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.assignment.R
import com.example.assignment.databinding.FragmentFavoriteBinding

class FavoriteFragment: Fragment(R.layout.fragment_favorite) {

    private var binding: FragmentFavoriteBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentFavoriteBinding = FragmentFavoriteBinding.bind(view)
        binding = fragmentFavoriteBinding
    }
}