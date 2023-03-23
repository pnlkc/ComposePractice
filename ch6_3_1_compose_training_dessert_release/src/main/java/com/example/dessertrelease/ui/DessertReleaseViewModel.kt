/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.dessertrelease.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dessertrelease.DessertReleaseApplication
import com.example.dessertrelease.R
import com.example.dessertrelease.data.UserPreferencesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/*
 * View model of Dessert Release components
 */
class DessertReleaseViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    //    private val _uiState = MutableStateFlow(DessertReleaseUiState())
    // UI states access for various [DessertReleaseUiState]
    /**
     * 콜드 Flow vs 핫 Flow
     *
     * 콜드 Flow는 데이터를 생성하기 시작하기 전에는 아무것도 하지 않는 비활성 상태에서 시작합니다.
     * 즉, flowOf나 callbackFlow와 같은 함수를 사용하여 Flow를 생성하고,
     * Flow의 수집자가 등록될 때까지 데이터 생성을 지연시킵니다.
     * 따라서 콜드 Flow는 수집자의 요청이 있을 때만 데이터를 생성합니다.
     * 콜드 Flow는 여러 수집자가 구독할 수 있으며, 각각의 수집자에 대해 독립적인 데이터 스트림을 생성합니다.
     *
     * 핫 Flow는 데이터 생성과 동시에 수집자에게 데이터를 전송하는 활성 상태에서 시작합니다.
     * 즉, 데이터 생성과 수집자의 구독이 동시에 시작됩니다.
     * 따라서 핫 Flow는 수집자가 구독하기 이전의 데이터를 놓칠 수 있습니다.
     * 핫 Flow는 여러 수집자가 구독할 수 있지만, 모든 수집자는 동일한 데이터 스트림을 공유합니다.
     *
     * 예를 들어, 채팅 앱에서 실시간으로 메시지를 처리해야 한다면 핫 Flow를 사용하는 것이 적합합니다.
     * 반면에, 사용자가 리스트에서 아이템을 선택할 때마다 새로운 데이터를 로드해야 한다면 콜드 Flow를 사용하는 것이 적합합니다.
     */
    val uiState: StateFlow<DessertReleaseUiState> =
        userPreferencesRepository.isLinearLayout.map { isLinearLayout ->
            DessertReleaseUiState(isLinearLayout)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DessertReleaseUiState()
        )

    /*
     * [selectLayout] change the layout and icons accordingly and
     * save the selection in DataStore through [userPreferencesRepository]
     */
    fun selectLayout(isLinearLayout: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveLayoutPreference(isLinearLayout)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DessertReleaseApplication)
//                DessertReleaseViewModel(application.userPreferencesRepository)
                DessertReleaseViewModel(application.container.userPreferencesRepository)
            }
        }
    }
}

/*
 * Data class containing various UI States for Dessert Release screens
 */
data class DessertReleaseUiState(
    val isLinearLayout: Boolean = true,
    val toggleContentDescription: Int =
        if (isLinearLayout) R.string.grid_layout_toggle else R.string.linear_layout_toggle,
    val toggleIcon: Int =
        if (isLinearLayout) R.drawable.ic_grid_layout else R.drawable.ic_linear_layout,
)
