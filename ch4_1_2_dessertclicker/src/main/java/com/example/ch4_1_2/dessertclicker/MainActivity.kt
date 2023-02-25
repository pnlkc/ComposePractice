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
package com.example.ch4_1_2.dessertclicker

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.ch4_1_2.dessertclicker.data.Datasource.dessertList
import com.example.ch4_1_2.dessertclicker.ui.theme.DessertClickerTheme
import com.example.ch4_1_2.dessertclicker.model.Dessert
import com.example.ch4_1_2.dessertclicker.R

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        1) onCreate()
        - 모든 Activity에서 반드시 구현해야하는 메소드
        - Activity를 초기화하는데 1번만 호출됨
        - Activity의 UI 레이아웃을 지정해야 함
        - onCreate()는 Activity가 OS에서 메모리에 새 Activity 객체를 만드는 초기화(initialized) 작업 직후 한번 호출 됨
         */
        super.onCreate(savedInstanceState)

        Log.d(TAG, "MainActivity - onCreate() 호출됨")

        setContent {
            DessertClickerTheme {
                DessertClickerApp(desserts = dessertList)
            }
        }
    }

    override fun onStart() {
        /*
        2-1) onStart()
        - onStart()가 실행되면 Activity가 화면에 표시됨
        - onStart() 상태에서는 사용자가 앱과 상호작용할 수 없음
        - onCreate()와 달리 onStart()는 Activity의 수명주기 동안 시스템이 여러 번 호출 가능
        - 포그라운드 상태
         */
        Log.d(TAG, "MainActivity - onStart() 호출됨")
        super.onStart()
    }

    override fun onRestart() {
        /*
        2-2) onRestart()
        - onRestart()의 역할은 기본적으로 onStart()와 유사함
        - onRestart()는 Activity가 처음으로 초기화 될 때는 호출되지 않음
        - onRestart()는 Activity가 처음 생성된 이후 onStop() 상태에서 다시 포그라운드 상태가 되었을 때 수행해야할 작업이 필요한 경우 사용
        - 포그라운드 상태
         */
        Log.d(TAG, "MainActivity - onRestart() 호출됨")
        super.onRestart()
    }

    override fun onResume() {
        /*
        3) onResume()
        - onResume()이 실행되면 앱이 포커스를 가짐
        - onResume() 상태에서는 사용자가 앱과 상호작용할 수 있음
        - 포그라운드 상태
         */
        Log.d(TAG, "MainActivity - onResume() 호출됨")
        super.onResume()
    }

    override fun onPause() {
        /*
        4) onPause()
        - Activity에서 포커스가 사라지면 onPause()가 호출됨
        - 백그라운드 상태
        - ex) 카톡 알림이 와서 Activity의 일부를 가리면 onPause()가 호출 됨
         */
        Log.d(TAG, "MainActivity - onPause() 호출됨")
        super.onPause()
    }

    override fun onStop() {
        /*
        5) onStop()
        - Activity가 화면에 표시되지 않으면 onStop()이 호출됨
        - 이 때 Activity는 완전히 사라지는 것이 아니라 백그라운드에 위치하게 됨
        - 백그라운드 상태
        - ex) 홈버튼을 눌러 홈화면으로 나가기
         */
        Log.d(TAG, "MainActivity - onStop() 호출됨")
        super.onStop()
    }

    override fun onDestroy() {
        /*
        6) onDestroy()
        - onDestroy()는 Activity가 리소스(메모리)를 더 이상 사용하지 않도록 Activity와 하위 객체들을 소멸시킴
        - onCreate()와 마찬가지로 Activity 인스턴스의 생명주기에서 1번만 호출됨
        - ex) Configuration changes(구성변경) => 화면 회전, 언어 변경, 다크 모드 등
         */
        Log.d(TAG, "MainActivity - onDestroy() 호출됨")
        super.onDestroy()
    }
}

/**
 * Determine which dessert to show.
 */
fun determineDessertToShow(
    desserts: List<Dessert>,
    dessertsSold: Int
): Dessert {
    var dessertToShow = desserts.first()
    for (dessert in desserts) {
        if (dessertsSold >= dessert.startProductionAmount) {
            dessertToShow = dessert
        } else {
            // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
            // you'll start producing more expensive desserts as determined by startProductionAmount
            // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
            // than the amount sold.
            break
        }
    }

    return dessertToShow
}

/**
 * Share desserts sold information using ACTION_SEND intent
 */
private fun shareSoldDessertsInformation(intentContext: Context, dessertsSold: Int, revenue: Int) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            intentContext.getString(R.string.share_text, dessertsSold, revenue)
        )
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)

    try {
        startActivity(intentContext, shareIntent, null)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            intentContext,
            intentContext.getString(R.string.sharing_not_available),
            Toast.LENGTH_LONG
        ).show()
    }
}

@Composable
private fun DessertClickerApp(
    desserts: List<Dessert>
) {
    // 앱이 Configuration changes(구성 변경)으로 onDestroy()되고 다시 onCreate()되도
    // 계속 값을 유지하려면 remember가 아닌 rememberSaveable을 사용해야됨
    var revenue by rememberSaveable{ mutableStateOf(0) }
    var dessertsSold by rememberSaveable { mutableStateOf(0) }

    val currentDessertIndex by rememberSaveable { mutableStateOf(0) }

    var currentDessertPrice by rememberSaveable {
        mutableStateOf(desserts[currentDessertIndex].price)
    }
    var currentDessertImageId by rememberSaveable {
        mutableStateOf(desserts[currentDessertIndex].imageId)
    }

    Scaffold(
        topBar = {
            val intentContext = LocalContext.current
            AppBar(
                onShareButtonClicked = {
                    shareSoldDessertsInformation(
                        intentContext = intentContext,
                        dessertsSold = dessertsSold,
                        revenue = revenue
                    )
                }
            )
        }
    ) { contentPadding ->
        DessertClickerScreen(
            revenue = revenue,
            dessertsSold = dessertsSold,
            dessertImageId = currentDessertImageId,
            onDessertClicked = {

                // Update the revenue
                revenue += currentDessertPrice
                dessertsSold++

                // Show the next dessert
                val dessertToShow = determineDessertToShow(desserts, dessertsSold)
                currentDessertImageId = dessertToShow.imageId
                currentDessertPrice = dessertToShow.price
            },
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@Composable
private fun AppBar(
    onShareButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.padding(start = 16.dp),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h6,
        )
        IconButton(
            onClick = onShareButtonClicked,
            modifier = Modifier.padding(end = 16.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(R.string.share),
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun DessertClickerScreen(
    revenue: Int,
    dessertsSold: Int,
    @DrawableRes dessertImageId: Int,
    onDessertClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.bakery_back),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(dessertImageId),
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                        .align(Alignment.Center)
                        .clickable { onDessertClicked() },
                    contentScale = ContentScale.Crop,
                )
            }
            TransactionInfo(revenue = revenue, dessertsSold = dessertsSold)
        }
    }
}

@Composable
private fun TransactionInfo(
    revenue: Int,
    dessertsSold: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.White),
    ) {
        DessertsSoldInfo(dessertsSold)
        RevenueInfo(revenue)
    }
}

@Composable
private fun RevenueInfo(revenue: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.total_revenue),
            style = MaterialTheme.typography.h4
        )
        Text(
            text = "$${revenue}",
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.h4
        )
    }
}

@Composable
private fun DessertsSoldInfo(dessertsSold: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.dessert_sold),
            style = MaterialTheme.typography.h6
        )
        Text(
            text = dessertsSold.toString(),
            style = MaterialTheme.typography.h6
        )
    }
}

@Preview
@Composable
fun MyDessertClickerAppPreview() {
    DessertClickerTheme {
        DessertClickerApp(listOf(Dessert(R.drawable.cupcake, 5, 0)))
    }
}
