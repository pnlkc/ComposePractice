package com.example.ch5_2_5_naver_search_api.data

import com.example.ch5_2_5_naver_search_api.network.NSIApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val CLIENT_ID: String
    val CLIENT_SECRET: String
    val nsiRepository: NSIRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://openapi.naver.com/v1/search/"
    override val CLIENT_ID = "AY2qQ5LGJ5oqR44lX2Bf"
    override val CLIENT_SECRET = "ajmPMgaI8X"

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        // Gson 테스트 용
//        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService: NSIApiService by lazy {
        retrofit.create(NSIApiService::class.java)
    }

    override val nsiRepository: NSIRepository by lazy {
        DefaultNSIRepository(retrofitService)
    }
}