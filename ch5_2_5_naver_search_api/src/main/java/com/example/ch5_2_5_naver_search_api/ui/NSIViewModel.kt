package com.example.ch5_2_5_naver_search_api.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ch5_2_5_naver_search_api.NSIApplication
import com.example.ch5_2_5_naver_search_api.data.NSIRepository
import com.example.ch5_2_5_naver_search_api.network.Item
import com.example.ch5_2_5_naver_search_api.network.NSIData
import kotlinx.coroutines.launch
import retrofit2.await
import java.io.IOException

sealed interface NSIUiState {
    // Success는 값을 포함하기 때문에 data class로 선언
    data class Success(val data: List<Item>) : NSIUiState
    // Lodaing과 Error는 값을 포함하지 않기 때문에 클래스가 인스턴스 될 때마다 새로운 객체가 생성되는 것은
    // 메모리 낭비이기 때문에 object로 선언
    object Loading : NSIUiState
    object Error : NSIUiState
}

class NSIViewModel(private val nsiRepository: NSIRepository) : ViewModel() {
    var nsiUiState: NSIUiState by mutableStateOf(NSIUiState.Loading)
        private set

    var textInput by mutableStateOf("")
        private set

    fun getSearchImage(
        query: String,
        display: Int = 100,
        start: Int = 1,
        sort: String = "sim",
        filter: String = "all",
    ) {
        viewModelScope.launch {
            nsiUiState = try {
                val response = nsiRepository.getSearchImage(
                    NSIApplication().container.CLIENT_ID,
                    NSIApplication().container.CLIENT_SECRET,
                    query,
                    display,
                    start,
                    sort,
                    filter
                )
                NSIUiState.Success(response.items)
            } catch (e: IOException) {
                NSIUiState.Error
            }
        }
    }

    fun updateText(newText: String) {
        textInput = newText
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NSIApplication)
                val nsiRepository = application.container.nsiRepository
                NSIViewModel(nsiRepository)
            }
        }
    }
}