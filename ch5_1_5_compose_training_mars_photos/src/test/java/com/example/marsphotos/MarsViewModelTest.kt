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
    /*
     runTest를 사용해야 하위 코루틴들을 runTest라는 최상위 테스트 코루틴으로 계층화 할 수 있기 때문입니다.
     runTest라는 최상위 테스트 코루틴으로 계층화할 때의 장점은 아래와 같습니다.


     1) runTest 블록으로 감싸면 코루틴 실행 중 발생할 수 있는 예외를 캐치하여 처리할 수 있습니다.
     만약 runTest 블록으로 감싸지 않으면 예외가 발생했을 때 테스트가 중단되고 테스트 결과도 실패로 처리됩니다.
     하지만 runTest 블록으로 감싸면 예외를 캐치하고 테스트 결과를 실패로 처리하면서 테스트를 계속 진행할 수 있습니다.

     2) runTest 블록으로 감싸면 코루틴이 완료될 때까지 대기할 수 있습니다.
     코루틴은 비동기적으로 동작하기 때문에, 코루틴이 완료되기 전에 테스트가 종료될 수 있습니다.
     하지만 runTest 블록으로 감싸면 코루틴이 완료될 때까지 대기할 수 있으므로 테스트를 완료할 수 있습니다.

     때문에 코루틴을 테스트 할 때는 runTest 블록으로 감싸지 않아도 정상적으로 작동할 수 있지만 감싸서 테스트하는 것이 안전함
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun marsViewModel_getMarsPhotos_verifyMarsUiStateSuccess() = runTest {
        val marsViewModel = MarsViewModel(
            marsPhotoRepository = FakeNetworkMarsPhotosRepository()
        )
        assertEquals(
            MarsUiState.Success(
                "Success: ${FakeDataSource.photosList.size} Mars " +
                        "photos retrieved"
            ),
            marsViewModel.marsUiState
        )
    }
}