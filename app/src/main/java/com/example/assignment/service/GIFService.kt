package com.example.assignment.service

import com.example.assignment.dto.GIFDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GIFService {
    @GET("/v1/gifs/trending")
    fun getTrendingGIFList(
        @Query("api_key") apiKey: String
    ): Call<GIFDto>
}