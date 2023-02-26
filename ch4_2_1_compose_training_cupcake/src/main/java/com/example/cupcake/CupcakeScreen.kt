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
package com.example.cupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource.flavors
import com.example.cupcake.data.DataSource.quantityOptions
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen

// Navigation의 구성요소 3가지
// 1. NavController: 대상(즉, 앱의 화면) 간 이동을 담당합니다
// 2. NavGraph: 이동할 컴포저블 대상을 매핑합니다
// 3. NavHost: NavGraph의 현재 대상을 표시하는 컨테이너 역할을 하는 컴포저블입니다

// navHostController는 NavHost 컴포저블과 함께 사용할 추가 기능을 제공하는 NavController 클래스의 서브클래스입니다
// 자세한 내용은 navHostController 클래스 참조

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */

enum class CupcakeScreen(@StringRes val title: Int) {
    Start(R.string.app_name),
    Flavor(R.string.choose_flavor),
    Pickup(R.string.choose_pickup_date),
    Summary(R.string.order_summary)
}

// codeLab 코드랑 차이가 생김
// github 코드를 참고해서 해결함
@Composable
fun CupcakeAppBar(
    currentScreenName: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = CupcakeScreen.valueOf(currentScreenName).title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CupcakeApp(modifier: Modifier = Modifier, viewModel: OrderViewModel = viewModel()){
    // TODO: Create NavController
    val navController = rememberNavController()
    // TODO: Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // TODO: Get the name of the current screen


    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreenName = backStackEntry?.destination?.route ?: CupcakeScreen.Start.name,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        // TODO: add NavHost
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            // 시작 화면
            composable(
                route = CupcakeScreen.Start.name
            ) {
                StartOrderScreen(
                    quantityOptions = quantityOptions,
                    onNextButtonClicked = {
                        viewModel.setQuantity(it)
                        // Flavor 화면 호출 (route가 CupcakeScreen.Flavor.name인 composable() 호출)
                        navController.navigate(CupcakeScreen.Flavor.name)
                    }
                )
            }

            // 맛 선택 화면
            composable(
                route = CupcakeScreen.Flavor.name
            ) {
                val context = LocalContext.current
                SelectOptionScreen(
                    subtotal = uiState.price,
//                    options = flavors.map { id -> stringResource(id) },
                    options = flavors.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setFlavor(it) },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Pickup.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    }
                )
            }

            // 수령일 선택 화면
            composable(
                route = CupcakeScreen.Pickup.name
            ) {
                SelectOptionScreen(
                    subtotal = uiState.price,
                    options = uiState.pickupOptions,
                    onSelectionChanged = { viewModel.setDate(it) },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Summary.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    }
                )
            }

            // 주문 요약 화면
            composable(
                route = CupcakeScreen.Summary.name
            ) {
                val context = LocalContext.current
                OrderSummaryScreen(
                    orderUiState = uiState,
                    onSendButtonClicked = { subject: String, summary: String ->
                        shareOrder(context, subject, summary)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    }
                )
            }
        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    // 백스택에 있는 화면이 Start 화면까지 전부 삭제 됨
    navController.popBackStack(
        CupcakeScreen.Start.name,
        inclusive = false
    )
}

private fun shareOrder(context: Context, subject: String, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply { 
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}