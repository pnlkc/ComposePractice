package com.example.marsphotos.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET

// 기본 URL를 상수로 선언
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

// Retrofit 객체 생성
private val retrofit = Retrofit.Builder()
    // Retrofit으로 받아온 JSON 데이터를 코틀린 객체로 변환 해주는 kotlinx serialization를 Retrofit 객체에 추가
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    // Retrofit에 웹 서비스의 기본 URL을 추가
    .baseUrl(BASE_URL)
    // build()를 통해 Retrofit 객체 생성
    .build()

// Retrofit이 HTTP 요청을 사용하여 웹 서버와 통신하는 방법을 정의하는 인터페이스
interface MarsApiService {
    // @GET 어노테이션을 통해 Retrofit에 getPhotos() 메소드가 HTTP의 GET 메소드임을 알리고
    // "photos"를 통해 /photos가 웹 서비스 메소드의 엔드포인트임을 알립니다
    // 엔드포인트는 서버에서 실행되는 웹 서비스에 액세스할 수 있는 URL입니다
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}

// 싱글톤 객체
object MarsApi {
    // by lazy를 사용하면 실제로 객체가 사용될 때 초기화 됨
    // by lazy는 lateinit과는 다르게 val로만 선언 가능
    val retrofitService: MarsApiService by lazy {
        // MarsApiService 인터페이스를 사용하여 retrofit.create() 메서드로 retrofitService 변수를 초기화
        retrofit.create(MarsApiService::class.java)
    }
}