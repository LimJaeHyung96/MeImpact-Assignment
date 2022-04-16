package com.example.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.assignment.database.AppDatabase
import com.example.assignment.dto.GIFDto
import com.example.assignment.favorite.FavoriteFragment
import com.example.assignment.service.GIFService
import com.example.assignment.trending.TrendingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.bottomNavigationView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val trendingFragment = TrendingFragment()
        val favoriteFragment = FavoriteFragment()

        replaceFragment(trendingFragment)

        bottomNavigationView.setOnItemSelectedListener { items ->
            when (items.itemId) {
                R.id.trendingGIF -> replaceFragment(trendingFragment)
                R.id.favorite -> replaceFragment(favoriteFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.mainFrameLayout, fragment)
                commit()
            }
    }
}