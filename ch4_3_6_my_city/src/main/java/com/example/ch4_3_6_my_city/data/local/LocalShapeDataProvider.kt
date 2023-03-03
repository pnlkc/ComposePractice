package com.example.ch4_3_6_my_city.data.local

import com.example.ch4_3_6_my_city.R
import com.example.ch4_3_6_my_city.data.Shape
import com.example.ch4_3_6_my_city.data.ShapeType

// 모양 리스트
object LocalShapeDataProvider {
    val allShapeList = listOf(
        Shape(
            name = "circle",
            picture = R.drawable.circle_red,
            shapeType = ShapeType.CIRCLE),
        Shape(
            name = "diamond",
            picture = R.drawable.diamond_blue,
            shapeType = ShapeType.DIAMOND),
        Shape(
            name = "pentagon",
            picture = R.drawable.pentagon_green,
            shapeType = ShapeType.PENTAGON
        ),
        Shape(
            name = "square",
            picture = R.drawable.square_purple,
            shapeType = ShapeType.SQUARE),
        Shape(
            name = "triangle",
            picture = R.drawable.triangle_yellow,
            shapeType = ShapeType.TRIANGLE
        ),
        Shape(
            name = "cross",
            picture = R.drawable.cross_red,
            shapeType = ShapeType.CROSS),
    )
}