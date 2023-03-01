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

package com.example.reply

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.reply.ui.ReplyApp
import com.example.reply.ui.theme.ReplyTheme

/**
 * Activity for Reply app
 */
class MainActivity : ComponentActivity() {

    // 이 오류 메시지는 현재 material3-window-size-class API가 이 주석을 필요로 하는 알파 버전이기 때문에 표시됩니다
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ReplyTheme {

                // 앱의 창 크기를 저장하는 변수
                val windowSize = calculateWindowSizeClass(activity = this)
                ReplyApp(windowSize = windowSize.widthSizeClass)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ReplyAppPreview() {
    ReplyTheme {
        ReplyApp(windowSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun ReplyAppMediumPreview() {
    ReplyTheme {
        ReplyApp(windowSize = WindowWidthSizeClass.Medium)
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun ReplyAppExpandedPreview() {
    ReplyTheme {
        ReplyApp(windowSize = WindowWidthSizeClass.Expanded)
    }
}