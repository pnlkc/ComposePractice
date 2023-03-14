package com.example.ch5_2_5_naver_search_api.network

import com.example.ch5_2_5_naver_search_api.NSIApplication
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NSIApiService {
    @GET("image")
    suspend fun getSearchImage(
        @Header("X-Naver-Client-Id") clientId: String = NSIApplication().container.CLIENT_ID,
        @Header("X-Naver-Client-Secret") clientSecret: String = NSIApplication().container.CLIENT_SECRET,
        @Query("query") query: String,
        @Query("display") display: Int? = 100,
        @Query("start") start: Int? = null,
        @Query("sort") sort: String = "sim",
        @Query("filter") filter: String? = null
    ): NSIData
}