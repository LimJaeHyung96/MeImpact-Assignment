package com.example.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.assignment.favorite.FavoriteFragment
import com.example.assignment.trending.TrendingFragment

class MainActivity : AppCompatActivity() {

    private val mainFrameLayout: FrameLayout by lazy {
        findViewById(R.id.mainFrameLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val trendingFragment = TrendingFragment()
        val favoriteFragment = FavoriteFragment()

    }
}