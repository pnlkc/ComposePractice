package com.example.marsphotos.network

import retrofit2.http.GET

// Retrofit이 HTTP 요청을 사용하여 웹 서버와 통신하는 방법을 정의하는 인터페이스
interface MarsApiService {
    // @GET 어노테이션을 통해 Retrofit에 getPhotos() 메소드가 HTTP의 GET 메소드임을 알리고
    // "photos"를 통해 /photos가 웹 서비스 메소드의 엔드포인트임을 알립니다
    // 엔드포인트는 서버에서 실행되는 웹 서비스에 액세스할 수 있는 URL입니다
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}