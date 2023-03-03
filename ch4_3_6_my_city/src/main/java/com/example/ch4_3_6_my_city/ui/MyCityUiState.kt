package com.example.ch4_3_6_my_city.ui

import com.example.ch4_3_6_my_city.data.Shape
import com.example.ch4_3_6_my_city.data.ShapeType
import com.example.ch4_3_6_my_city.data.ShapeWithColor
import com.example.ch4_3_6_my_city.data.local.LocalShapeWithColorDataProvider

data class MyCityUiState(
    val shapes: List<Shape> = emptyList(),
    val shapeWithColors: Map<ShapeType, List<ShapeWithColor>> = emptyMap(),
    val currentShape: ShapeType = ShapeType.NOTHING,
    val currentSelectedShapeWithColor: ShapeWithColor = LocalShapeWithColorDataProvider.defaultShapeWithColor,
)