package com.example.ch4_1_7.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.ch4_1_7.dessertclicker.data.Datasource.dessertList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertClickerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DessertClickerUiState())
    val uiState = _uiState.asStateFlow()

    fun onDessertClicked() {
        _uiState.update { currentState ->
            val nextDessertSold = currentState.dessertsSold + 1
            val nextIndex = determineDessertToShow(nextDessertSold)

            currentState.copy(
                revenue = currentState.revenue + currentState.currentDessertPrice,
                dessertsSold = nextDessertSold,
                currentDessertIndex = nextIndex,
                currentDessertImageId = dessertList[nextIndex].imageId,
                currentDessertPrice = dessertList[nextIndex].price
            )
        }
    }

    private fun determineDessertToShow(dessertsSold: Int): Int {
        var dessertIndex = 0
        for (i in dessertList.indices) {
            if (dessertsSold >= dessertList[i].startProductionAmount) {
                dessertIndex = i
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }
        return dessertIndex
    }
}