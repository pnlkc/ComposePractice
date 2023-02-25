package com.example.ch4_1_7.dessertclicker.ui

import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.ch4_1_7.dessertclicker.data.Datasource.dessertList

data class DessertClickerUiState(
    val revenue: Int = 0,
    val dessertsSold: Int = 0,
    val currentDessertIndex: Int = 0,
    val currentDessertPrice: Int = dessertList[0].price,
    @DrawableRes val currentDessertImageId: Int = dessertList[0].imageId
)