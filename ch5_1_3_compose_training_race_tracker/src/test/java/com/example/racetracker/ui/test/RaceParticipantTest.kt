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

package com.example.racetracker.ui.test

import com.example.racetracker.ui.RaceParticipant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.exp

@OptIn(ExperimentalCoroutinesApi::class)
class RaceParticipantTest {
    private val raceParticipant = RaceParticipant(
        name = "Test",
        maxProgress = 100,
        progressDelayMillis = 500L,
        initialProgress = 0,
        progressIncrement = 1
    )

    @Test
    fun raceParticipant_RaceStarted_ProgressUpdated() = runTest {
        val expectedProgress = 1

        // launch로 raceParticipant.run()을 감싸는 이유
        // runtTest 빌더에서 직접 raceParticipant.run()을 호출할 수 있지만 기본 테스트 구현에서는 delay() 호출을 무시합니다.
        // 따라서 run() 실행이 완료된 후에 진행률을 분석할 수 있습니다.
        launch { raceParticipant.run() }
        // 테스트 할 때 시간을 특정 값만큼 진행시키는 기능
        advanceTimeBy(raceParticipant.progressDelayMillis)
        // advanceTimeBy() 진행된 시간의 작업을 실행시키는 기능
        runCurrent()
        assertEquals(expectedProgress, raceParticipant.currentProgress)
    }

    @Test
    fun raceParticipant_RaceFinished_ProgressUpdated() = runTest {
        launch { raceParticipant.run() }
        advanceTimeBy(raceParticipant.maxProgress * raceParticipant.progressDelayMillis)
        runCurrent()
        assertEquals(100, raceParticipant.currentProgress)
    }

    @Test
    fun raceParticipant_RacePaused_ProgressUpdated() = runTest {
        val expectedProgress = 10
        val job = launch { raceParticipant.run() }
        advanceTimeBy(expectedProgress * raceParticipant.progressDelayMillis)
        runCurrent()
        // Job 코루틴을 중지하고 중지가 끝날 때까지 정지하도록 하는 코드
        job.cancelAndJoin()
        assertEquals(expectedProgress, raceParticipant.currentProgress)
    }

    @Test
    fun raceParticipant_RaceResumed_ProgressUpdated() = runTest {
        val firstExpectedProgress = 5
        val job = launch { raceParticipant.run() }
        advanceTimeBy(firstExpectedProgress * raceParticipant.progressDelayMillis)
        runCurrent()
        job.cancelAndJoin()

        val secondExpectedProgress = 5
        launch { raceParticipant.run() }
        advanceTimeBy(secondExpectedProgress * raceParticipant.progressDelayMillis)
        runCurrent()

        assertEquals(firstExpectedProgress + secondExpectedProgress, raceParticipant.currentProgress)
    }

    // Exception Thrown Test는 (expected = IllegalArgumentException::class) 이런 방법으로 진행 됨
    @Test (expected = IllegalArgumentException::class)
    fun raceParticipant_MaxProgressValueIsZero_IllegalArgumentExceptionThrown() {
        RaceParticipant(name = "maxProgressValueIsZero", maxProgress = 0)
    }

    // Exception Thrown Test는 (expected = IllegalArgumentException::class) 이런 방법으로 진행 됨
    @Test (expected = IllegalArgumentException::class)
    fun raceParticipant_ProgressIncrementValueIsZero_IllegalArgumentExceptionThrown() {
        RaceParticipant(name = "progressIncrementValueIsZero", progressIncrement = 0)
    }
}
