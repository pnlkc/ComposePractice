package com.example.ch4_3_6_my_city

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ch4_3_6_my_city.ui.MyCityApp
import com.example.ch4_3_6_my_city.ui.theme.MyCityTheme


/*
이 앱은 다음 요건을 충족해야 합니다.

1. 화면이 여러 개 포함되어 있습니다. 예를 들어 각 화면에 다른 추천 카테고리가 표시될 수 있습니다.
2. 사용자의 앱 탐색 지원에 Jetpack Navigation 구성요소를 사용합니다.
3. UI 레이어와 데이터 레이어를 명확하게 구별합니다.
4. ViewModel을 사용하고, 단방향 데이터 흐름(UDF) 패턴을 사용하여 뷰 모델에서 UI를 업데이트합니다.
5. 다양한 화면 크기를 모두 고려하는 적응형 레이아웃을 사용합니다.
6. 적응형 디자인 및 탐색에 관한 Material Design 가이드라인을 따릅니다.
 */

/*
MyCity 앱은 나중에 코드랩에서 검색하기 편하도록 앱이름을 그대로 했지만
지금까지 배운 기술로 여러가지 모양의 도형을 리스트에 보여주고
도형을 클릭하면 여러가지 색의 도형을 보여주고
한번 더 클릭하면 세부 화면으로 이동해서 보여주는 방식으로 구성할 예정
 */
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCityTheme {
                // 화면 크기 자동 측정
                val windowSize = calculateWindowSizeClass(activity = this)
                MyCityApp(
                    windowSize = windowSize.widthSizeClass
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyCityAppPreview() {
    MyCityTheme {
        Surface {
            MyCityApp(windowSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun MyCityAppExpandedPreview() {
    MyCityTheme {
        Surface {
            MyCityApp(windowSize = WindowWidthSizeClass.Expanded)
        }
    }
}

