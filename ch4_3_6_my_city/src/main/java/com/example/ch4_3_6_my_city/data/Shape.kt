package com.example.ch4_3_6_my_city.data

import androidx.annotation.DrawableRes

// 모양 리스트의 아이템의 데이터 클래스
data class Shape(
    // 리스트에 표시될 이름
    val name: String,
    // 리스트에 표시될 사진
    @DrawableRes val picture: Int,
    // 모양 정보
    val shapeType: ShapeType
)
