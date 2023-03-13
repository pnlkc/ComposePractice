package com.example.ch5_2_4_amphibians.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://android-kotlin-fun-mars-server.appspot.com/amphibians 의 데이터를 담을 데이터 클래스
@Serializable
data class AmphibiansData(
    val name: String,
    val type: String,
    val description: String,
    @SerialName(value = "img_src") val imgSrc: String,
)
