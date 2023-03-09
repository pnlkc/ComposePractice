package com.example.marsphotos

import com.example.marsphotos.fake.FakeDataSource
import com.example.marsphotos.fake.FakeNetworkMarsPhotosRepository
import com.example.marsphotos.fake.rules.TestDispatcherRule
import com.example.marsphotos.ui.screens.MarsUiState
import com.example.marsphotos.ui.screens.MarsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MarsViewModelTest {
    // MarsViewModel이 init을 할 때 getMarsPhotos() 메소드가 실행되는데
    // getMarsPhotos() 안에 viewModelScope.launch 코드가 있어서
    // 테스트 룰을 설정해주지 않으면 Dispatcher 에러가 발생
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    // 궁금증? => runTest를 안해도 에러가 발생하지 않는 이유가 궁금
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun marsViewModel_getMarsPhotos_verifyMarsUiStateSuccess() = runTest {
        val marsViewModel = MarsViewModel(
            marsPhotoRepository = FakeNetworkMarsPhotosRepository()
        )
        assertEquals(
            MarsUiState.Success("Success: ${FakeDataSource.photosList.size} Mars " +
                    "photos retrieved"),
            marsViewModel.marsUiState
        )
    }
}