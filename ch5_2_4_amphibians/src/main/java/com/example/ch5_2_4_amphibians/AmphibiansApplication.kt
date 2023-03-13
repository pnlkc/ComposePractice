package com.example.ch5_2_4_amphibians

import android.app.Application
import com.example.ch5_2_4_amphibians.data.AppContainer
import com.example.ch5_2_4_amphibians.data.DefaultAppContainer

// 매니페스트 추가 필요
class AmphibiansApplication : Application() {
//    lateinit var container: AppContainer
//
//    override fun onCreate() {
//        super.onCreate()
//        container = DefaultAppContainer()
//    }

    // 위의 코드가 코드랩에서 사용하던 패턴인데 아래코드로도 사용가능한지 궁금?
    val container: AppContainer by lazy {
        DefaultAppContainer()
    }
}