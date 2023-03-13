package com.example.ch5_2_4_amphibians.ui

import android.text.Spannable.Factory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ch5_2_4_amphibians.AmphibiansApplication
import com.example.ch5_2_4_amphibians.data.AmphibiansRepository
import com.example.ch5_2_4_amphibians.network.AmphibiansData
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface AmphibiansUiState {
    // Success는 값을 포함하기 때문에 data class로 선언
    data class Success(val data: List<AmphibiansData>) : AmphibiansUiState
    // Lodaing과 Error는 값을 포함하지 않기 때문에 클래스가 인스턴스 될 때마다 새로운 객체가 생성되는 것은
    // 메모리 낭비이기 때문에 object로 선언
    object Loading : AmphibiansUiState
    object Error : AmphibiansUiState
}

class AmphibiansViewModel(private val amphibiansRepository: AmphibiansRepository) : ViewModel() {
    // private set은 외부에서 getter 메소드를 통해 값을 읽을 수 있지만
    // private var로 선언하면 변수 자체를 외부에서 접근할 수 없습니다.
    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set

    init {
        getAmphibiansData()
    }

    fun getAmphibiansData() {
        viewModelScope.launch {
            amphibiansUiState = try {
                AmphibiansUiState.Success(amphibiansRepository.getData())
            } catch (e: IOException) {
                AmphibiansUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibiansApplication)
                val amphibiansRepository = application.container.amphibiansRepository
                AmphibiansViewModel(amphibiansRepository)
            }
        }
    }
}