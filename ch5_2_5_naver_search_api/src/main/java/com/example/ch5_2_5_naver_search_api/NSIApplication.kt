package com.example.ch5_2_5_naver_search_api

import android.app.Application
import com.example.ch5_2_5_naver_search_api.data.AppContainer
import com.example.ch5_2_5_naver_search_api.data.DefaultAppContainer

class NSIApplication : Application() {
    val container: AppContainer by lazy {
        DefaultAppContainer()
    }
}