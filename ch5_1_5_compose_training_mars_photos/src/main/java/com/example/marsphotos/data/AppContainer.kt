package com.example.marsphotos.data

import com.example.marsphotos.network.MarsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

// 의존성 주입을 위한 컨테이너
// 컨테이너는 앱에 필요한 의존성 항목들이 포함된 객체
// 컨테이너에 포함된 항목들은 앱 전체에 걸쳐 사용
interface AppContainer {
    val marsPhotosRepository: MarsPhotoRepository
}

class DefaultAppContainer : AppContainer {
    // 기본 URL 선언
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

    // Retrofit 객체 생성
    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        // Retrofit으로 받아온 JSON 데이터를 코틀린 객체로 변환 해주는 kotlinx serialization를 Retrofit 객체에 추가
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
        // Retrofit에 웹 서비스의 기본 URL을 추가
        .baseUrl(BASE_URL)
        // build()를 통해 Retrofit 객체 생성
        .build()

    private val retrofitService: MarsApiService by lazy {
        // MarsApiService 인터페이스를 사용하여 retrofit.create() 메서드로 retrofitService 변수를 초기화
        retrofit.create(MarsApiService::class.java)
    }

    override val marsPhotosRepository: MarsPhotoRepository by lazy {
        DefaultMarsPhotosRepository(retrofitService)
    }
}