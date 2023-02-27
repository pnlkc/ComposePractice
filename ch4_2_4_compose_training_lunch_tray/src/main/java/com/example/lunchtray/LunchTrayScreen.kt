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
package com.example.lunchtray

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.datasource.DataSource
import com.example.lunchtray.ui.*

// TODO: Screen enum
enum class LunchTrayScreen(@StringRes val title: Int) {
    Start(R.string.app_name),
    EntreeMenu(R.string.choose_entree),
    SideDishMenu(R.string.choose_side_dish),
    AccompanimentMenu(R.string.choose_accompaniment),
    Checkout(R.string.order_checkout)
}

// TODO: AppBar
@Composable
fun LunchTrayAppBar(
    currentScreenName: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(stringResource(id = LunchTrayScreen.valueOf(currentScreenName).title)) },
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
fun LunchTrayApp(modifier: Modifier = Modifier) {
    // TODO: Create Controller and initialization
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()


    // Create ViewModel
    val viewModel: OrderViewModel = viewModel()

    Scaffold(
        topBar = {
            // TODO: AppBar
            LunchTrayAppBar(
                currentScreenName = backStackEntry?.destination?.route
                    ?: LunchTrayScreen.Start.name,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        // TODO: Navigation host
        NavHost(
            navController = navController,
            startDestination = LunchTrayScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            // 시작(메인) 화면
            composable(
                route = LunchTrayScreen.Start.name
            ) {
                StartOrderScreen(
                    onStartOrderButtonClicked = {
                        navController.navigate(LunchTrayScreen.EntreeMenu.name)
                    }
                )
            }

            composable(
                route = LunchTrayScreen.EntreeMenu.name
            ) {
                EntreeMenuScreen(
                    options = DataSource.entreeMenuItems,
                    onCancelButtonClicked = { cancelButtonClicked(viewModel, navController) },
                    onNextButtonClicked = { navController.navigate(LunchTrayScreen.SideDishMenu.name) },
                    onSelectionChanged = { entree -> viewModel.updateEntree(entree) }
                )
            }

            composable(
                route = LunchTrayScreen.SideDishMenu.name
            ) {
                SideDishMenuScreen(
                    options = DataSource.sideDishMenuItems,
                    onCancelButtonClicked = { cancelButtonClicked(viewModel, navController) },
                    onNextButtonClicked = { navController.navigate(LunchTrayScreen.AccompanimentMenu.name) },
                    onSelectionChanged = { sideDish -> viewModel.updateSideDish(sideDish) }
                )
            }

            composable(
                route = LunchTrayScreen.AccompanimentMenu.name
            ) {
                AccompanimentMenuScreen(
                    options = DataSource.accompanimentMenuItems,
                    onCancelButtonClicked = { cancelButtonClicked(viewModel, navController) },
                    onNextButtonClicked = { navController.navigate(LunchTrayScreen.Checkout.name) },
                    onSelectionChanged = { accompaniment -> viewModel.updateAccompaniment(accompaniment) }
                )
            }

            composable(
                route = LunchTrayScreen.Checkout.name
            ) {
                CheckoutScreen(
                    orderUiState = uiState,
                    // 결제 화면에서 제출 버튼을 누르면 시작 화면으로 이동합니다
                    // viewModel.resetOrder()를 실행 후 백스택을 지우고 메인화면으로 이동
                    // cancel 버튼을 눌렀을 때와 동일한 기능
                    onNextButtonClicked = { cancelButtonClicked(viewModel, navController) },
                    onCancelButtonClicked = { cancelButtonClicked(viewModel, navController) }
                )
            }
        }
    }
}

private fun cancelButtonClicked(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(
        LunchTrayScreen.Start.name,
        // true면 팝업 할 때 위에 지정된 화면도 지우고 그 이전화면으로 팝업함
        inclusive = false
    )
}


