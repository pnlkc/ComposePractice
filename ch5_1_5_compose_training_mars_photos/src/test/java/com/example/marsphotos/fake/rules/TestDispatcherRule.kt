package com.example.marsphotos.fake.rules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

// 로컬 단위 테스트에서 Android UI 스레드를 사용할 수 없기 때문에 로컬 단위 테스트에서는 Dispatchers.Main을 사용하면
// java.lang.IllegalStateException: Module with the Main dispatcher had failed to initialize 에러가 발생 함
// 실제 UI 스레드가 제공되는 계측 테스트에서는 Main 디스패처를 교체해서는 안 됩니다
// 따라서 로컬 단위 테스트용 Dispatcher를 따로 만들어야 함
@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}