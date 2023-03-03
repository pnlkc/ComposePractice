package com.example.ch4_3_6_my_city.data

import androidx.annotation.DrawableRes

// 색깔 리스트의 아이템의 데이터 클래스
data class ShapeWithColor(
    // 리스트에 표시될 이름
    val name: String,
    // 세부 화면에 표시될 정보
    val description: String,
    // 리스트와 세부 화면에 표시될 사진
    @DrawableRes val picture: Int
)
