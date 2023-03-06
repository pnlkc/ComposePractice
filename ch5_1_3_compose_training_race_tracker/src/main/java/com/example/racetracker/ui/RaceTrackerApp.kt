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

package com.example.racetracker.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.racetracker.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

// 코루틴은 구조화된 동시실행
// 즉, 코루틴은 계층 구조가 있어서 상위 작업과 하위 작업이 존재
@Composable
fun RaceTrackerApp() {
    /**
     * Note: To survive the configuration changes such as screen rotation, [rememberSaveable] should
     * be used with custom Saver object. But to keep the example simple, and keep focus on
     * Coroutines that implementation detail is stripped out.
     */
    val playerOne = remember {
        RaceParticipant(name = "Player 1", progressIncrement = 1)
    }
    val playerTwo = remember {
        RaceParticipant(name = "Player 2", progressIncrement = 2)
    }
    var raceInProgress by remember { mutableStateOf(false) }

    // 레이스가 시작 되었는지 확인
    if (raceInProgress) {
        // Composable에서 코루틴(suspend fun)을 실행하려면 LaunchedEffect()라는 Composable을 사용해야함!!
        // LaunchedEffect()의 인자인 key값이 변경되면 기본 코루틴이 취소되고 다시 실행 됨
        LaunchedEffect(key1 =  playerOne, key2 = playerTwo) {
            Log.d("코루틴 스코프 확인용 로그", "${this.coroutineContext} - LaunchedEffect Scope")

            // coroutineScope 블록은 블록 내부의 모든 코드가 실행이 완료되어야 반환되고 계속 진행됨
            coroutineScope {
                Log.d("코루틴 스코프 확인용 로그", "${this.coroutineContext} - coroutineScope before run Scope")

                launch {
                    playerOne.run()
                    Log.d("코루틴 스코프 확인용 로그", "${this.coroutineContext} - Launch(playerOne.run()) Scope")
                }

                launch {
                    playerTwo.run()
                    Log.d("코루틴 스코프 확인용 로그", "${this.coroutineContext} - Launch(playerTwo.run()) Scope")
                }
            }

            Log.d("코루틴 스코프 확인용 로그", "${this.coroutineContext} - coroutineScope after run Scope")
            raceInProgress = false
        }
    }

    RaceTrackerScreen(
        playerOne = playerOne,
        playerTwo = playerTwo,
        isRunning = raceInProgress,
        onRunStateChange = { raceInProgress = it }
    )
}

@Composable
private fun RaceTrackerScreen(
    playerOne: RaceParticipant,
    playerTwo: RaceParticipant,
    isRunning: Boolean,
    onRunStateChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.run_a_race),
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_walk),
                contentDescription = null,
                modifier = Modifier.padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )
            StatusIndicator(
                participantName = playerOne.name,
                currentProgress = playerOne.currentProgress,
                maxProgress = stringResource(
                    R.string.progress_percentage,
                    playerOne.maxProgress
                ),
                progressFactor = playerOne.progressFactor
            )
            Spacer(modifier = Modifier.size(24.dp))
            StatusIndicator(
                participantName = playerTwo.name,
                currentProgress = playerTwo.currentProgress,
                maxProgress = stringResource(
                    R.string.progress_percentage,
                    playerTwo.maxProgress
                ),
                progressFactor = playerTwo.progressFactor
            )
            RaceControls(
                isRunning = isRunning,
                onRunStateChange = onRunStateChange,
                onReset = {
                    playerOne.reset()
                    playerTwo.reset()
                    onRunStateChange(false)
                }
            )
        }
    }
}

@Composable
private fun StatusIndicator(
    participantName: String,
    currentProgress: Int,
    maxProgress: String,
    progressFactor: Float,
    modifier: Modifier = Modifier
) {
    Row {
        Text(participantName, Modifier.padding(end = 8.dp))

        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LinearProgressIndicator(
                progress = progressFactor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.progress_percentage, currentProgress),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = maxProgress,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun RaceControls(
    onRunStateChange: (Boolean) -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
    isRunning: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { onRunStateChange(!isRunning) }) {
            Text(if (isRunning) stringResource(R.string.pause) else stringResource(R.string.start))
        }

        Button(onClick = onReset) {
            Text(stringResource(R.string.reset))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RaceTrackerAppPreview() {
    MaterialTheme {
        RaceTrackerApp()
    }
}
