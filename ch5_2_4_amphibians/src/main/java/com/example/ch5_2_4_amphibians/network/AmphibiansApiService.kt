package com.example.ch5_2_4_amphibians.network

import retrofit2.http.GET

interface AmphibiansApiService {
    @GET("amphibians")
    suspend fun getData() : List<AmphibiansData>
}