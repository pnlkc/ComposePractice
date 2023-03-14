package com.example.ch5_2_5_naver_search_api.network

import kotlinx.serialization.Serializable

// kotlinx serialization는 Json의 모든 필드를 data class에 정의 해야 됨
// Gson은 son의 모든 필드를 data class에 정의할 필요 없음
@Serializable
data class NSIData(
    val lastBuildDate: String = "",
    val total: Int = 0,
    val start: Int = 0,
    val display: Int = 0,
    val items: List<Item> = listOf()
)

@Serializable
data class Item(
    val title: String = "",
    val link: String = "",
    val thumbnail: String = "",
    val sizeheight: String = "",
    val sizewidth: String = ""
)