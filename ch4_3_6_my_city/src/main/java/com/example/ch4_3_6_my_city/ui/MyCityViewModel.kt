package com.example.ch4_3_6_my_city.ui

import androidx.lifecycle.ViewModel
import com.example.ch4_3_6_my_city.data.ShapeType
import com.example.ch4_3_6_my_city.data.ShapeWithColor
import com.example.ch4_3_6_my_city.data.local.LocalShapeDataProvider
import com.example.ch4_3_6_my_city.data.local.LocalShapeWithColorDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyCityViewModel: ViewModel() {
    // uiState 변수 선언
    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initializeUIState()
    }

    // uiState 초기화하는 기능
    private fun initializeUIState() {
        val shapeWithColors: Map<ShapeType, List<ShapeWithColor>> = mapOf(
            ShapeType.CIRCLE to LocalShapeWithColorDataProvider.allCircleList,
            ShapeType.CROSS to LocalShapeWithColorDataProvider.allCrossList,
            ShapeType.SQUARE to LocalShapeWithColorDataProvider.allSquareList,
            ShapeType.DIAMOND to LocalShapeWithColorDataProvider.allDiamondList,
            ShapeType.PENTAGON to LocalShapeWithColorDataProvider.allPentagonList,
            ShapeType.TRIANGLE to LocalShapeWithColorDataProvider.allTriangleList,
            ShapeType.NOTHING to listOf()
        )

        _uiState.value = MyCityUiState(
            shapes = LocalShapeDataProvider.allShapeList,
            shapeWithColors = shapeWithColors
        )
    }

    // ShapeType을 업데이트하는 기능
    fun updateCurrentShapeType(shapeType: ShapeType) {
        _uiState.update {
            it.copy(
                currentShape = shapeType
            )
        }
    }

    // 선택된 ShapeWithColor를 업데이트하는 기능
    fun updateCurrentSelectedShapeWithColor(shapeWithColor: ShapeWithColor) {
        _uiState.update {
            it.copy(
                currentSelectedShapeWithColor = shapeWithColor
            )
        }
    }

    // 세부 화면에서 뒤로가기 했을 때
//    fun resetShapeAndColor() {
//        _uiState.update {
//            it.copy(
//                currentSelectedShapeWithColor = LocalShapeWithColorDataProvider.defaultShapeWithColor
//            )
//        }
//    }

    // 색상 선택에서 뒤로가기 했을 때
//    fun resetShape() {
//        _uiState.update {
//            it.copy(
//                currentShape = ShapeType.NOTHING
//            )
//        }
//    }
}