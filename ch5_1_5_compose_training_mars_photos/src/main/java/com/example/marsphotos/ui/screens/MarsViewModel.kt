/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marsphotos.ui.screens

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
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.data.DefaultMarsPhotosRepository
import com.example.marsphotos.data.MarsPhotoRepository
import kotlinx.coroutines.launch
import java.io.IOException

// 왜 enum 클래스를 사용하지 않고 sealed interface나 sealed class를 사용하는지에 대한 설명은 아래 링크 참고
// https://tourspace.tistory.com/467
/*
    아주 간단히 정리하면
    Enum Class는 클래스에 속한 value(값)들이 모두 같은 형태를 가져야 하는데 아래의 경우 처럼
    Success와 Error가
 */
sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel(private val marsPhotoRepository: MarsPhotoRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API
     */
    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = try {
                // 아래 코드처럼 쓰면 앱 아키텍쳐 원칙에 맞지 않음 (레이어 간의 의존성 문제 발생)
//                val listResult = MarsApi.retrofitService.getPhotos()

                val listResult = marsPhotoRepository.getMarsPhotos()

                MarsUiState.Success("Success: ${listResult.size} Mars photos retrieved")
            } catch (e: IOException) {
                MarsUiState.Error
            }
        }
    }

    /*
    안드로이드에서 ViewModel 클래스를 생성할 때는 ViewModel 클래스의 생성자를 호출할 수 없습니다.
    이는 안드로이드 프레임워크가 ViewModel 인스턴스의 생명주기를 관리하기 때문입니다.
    이러한 제한으로 인해 ViewModel에서 일반적으로 생성자에 전달되는 값을 ViewModel 클래스에 전달할 수 없게 됩니다.

    이러한 문제를 해결하기 위해, 안드로이드 프레임워크는 ViewModelProvider.Factory 인터페이스를 제공합니다.
    ViewModelProvider.Factory를 구현하면 ViewModelProvider에서 ViewModel 인스턴스를 생성할 때 원하는 값들을 전달할 수 있습니다.
    이를 통해 ViewModel 인스턴스를 생성할 때 필요한 의존성 객체를 전달하거나 초기 상태를 설정할 수 있습니다.
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotoRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotoRepository = marsPhotoRepository)
            }
        }
    }
}