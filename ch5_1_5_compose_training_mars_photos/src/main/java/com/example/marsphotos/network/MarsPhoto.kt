package com.example.marsphotos.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarsPhoto(
    // id라는 키를 가진 데이터 객체를 id 변수에 저장
    val id: String,
    // img_src 라는 키를 찾아 그 데이터 객체를 imgSrc 변수에 저장
    @SerialName(value = "img_src")
    val imgSrc: String
)