package com.example.ch4_3_6_my_city.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ch4_3_6_my_city.data.Shape
import com.example.ch4_3_6_my_city.data.ShapeWithColor
import com.example.ch4_3_6_my_city.data.local.LocalShapeDataProvider
import com.example.ch4_3_6_my_city.data.local.LocalShapeWithColorDataProvider
import com.example.ch4_3_6_my_city.ui.theme.MyCityTheme
import com.example.ch4_3_6_my_city.ui.theme.Typography
import com.example.ch4_3_6_my_city.ui.utils.MyCityContentType

// 각 화면의 타이틀을 알기 위한 enum 클래스
enum class MyCityScreen(val title: String) {
    Shape("Shape"),
    Color("Color"),
    Detail("Detail")
}

// MyCity 앱 전체 컴포저블
@Composable
fun MyCityApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    // viewModel 변수 선언
    val viewModel: MyCityViewModel = viewModel()

    // uiState를 viewModel에서 가져오기
    val myCityUiState = viewModel.uiState.collectAsState().value

    // 화면 크기에 따라 contentType 변경
    val contentType: MyCityContentType = when (windowSize) {
        // width : 0-599, height : 0-479
        WindowWidthSizeClass.Compact -> MyCityContentType.SHAPE_ONLY
        // width : 600-839, height : 480-899
        WindowWidthSizeClass.Medium -> MyCityContentType.SHAPE_AND_COLOR
        // width : 840+, height : 900+
        WindowWidthSizeClass.Expanded -> MyCityContentType.ALL_CONTENT
        else -> MyCityContentType.SHAPE_ONLY
    }

    // NavHostController 변수 선언
    val navController: NavHostController = rememberNavController()
    // backStackEntry를 asState()로 선언
    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            MyCityAppBar(
                currentScreenName = backStackEntry?.destination?.route ?: MyCityScreen.Shape.name,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            // 시작 화면을 Shape 화면으로 지정
            startDestination = MyCityScreen.Shape.name,
            modifier = modifier.padding(innerPadding)
        ) {
            // Shape 화면
            composable(
                route = MyCityScreen.Shape.name
            ) {
                ShapeScreen(
                    shapes = myCityUiState.shapes,
                    onClick = {
                        viewModel.updateCurrentShapeType(it.shapeType)
                        navController.navigate(MyCityScreen.Color.name)
                    }
                )
            }

            // Color 화면
            composable(
                route = MyCityScreen.Color.name
            ) {
                ColorScreen(
                    shapeWithColors = myCityUiState.shapeWithColors[myCityUiState.currentShape]!!,
                    onClick = {
                        viewModel.updateCurrentSelectedShapeWithColor(it)
                        navController.navigate(MyCityScreen.Detail.name)
                    }
                )
            }

            // Detail 화면
            composable(
                route = MyCityScreen.Detail.name
            ) {
                DetailScreen(
                    shapeWithColor = myCityUiState.currentSelectedShapeWithColor
                )
            }
        }
    }
}

// 모양 리스트 화면
@Composable
fun ShapeScreen(
    shapes: List<Shape>,
    onClick: (Shape) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(shapes, key = { shape -> shape.name }) { shape ->
            ShapeScreenItem(
                shape = shape,
                onClick = onClick
            )
        }
    }
}

// 모양 리스트 아이템 화면
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShapeScreenItem(
    shape: Shape,
    onClick: (Shape) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)),
        onClick = { onClick(shape) },
        elevation = 2.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(72.dp),
            horizontalArrangement = Arrangement.spacedBy(25.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Image(
                painter = painterResource(id = shape.picture),
                contentDescription = null,
                modifier = modifier.size(72.dp)
            )

            Text(
                text = shape.name,
                style = Typography.h1
            )
        }
    }
}

// 색깔 리스트 화면
@Composable
fun ColorScreen(
    shapeWithColors: List<ShapeWithColor>,
    onClick: (ShapeWithColor) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(shapeWithColors, key = { shapeWithColor -> shapeWithColor.name }) { shapeWithColor ->
            ColorScreenItem(
                shapeWithColor = shapeWithColor,
                onClick = onClick
            )
        }
    }
}

// 색깔 리스트 아이템 화면
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ColorScreenItem(
    shapeWithColor: ShapeWithColor,
    onClick: (ShapeWithColor) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)),
        onClick = { onClick(shapeWithColor) },
        elevation = 2.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(72.dp),
            horizontalArrangement = Arrangement.spacedBy(25.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Image(
                painter = painterResource(id = shapeWithColor.picture),
                contentDescription = null,
                modifier = modifier.size(72.dp)
            )

            Text(
                text = shapeWithColor.name,
                style = Typography.h1
            )
        }
    }
}

// 세부 정보 화면
@Composable
fun DetailScreen(
    shapeWithColor: ShapeWithColor,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Image(
            painter = painterResource(id = shapeWithColor.picture),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = shapeWithColor.description,
            style = Typography.h1
        )
    }
}

// 상단 앱바 구성
@Composable
fun MyCityAppBar(
    currentScreenName: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(MyCityScreen.valueOf(currentScreenName).title) },
        modifier = modifier,
        navigationIcon = if (canNavigateBack) {
            // 람다 형식으로 한번 감싸줘야 함
            {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        } else {
            // null 처리를 안하면 아이콘만 안나오고 공간을 차지함
            null
        }
    )
}

// ShapeScreen 미리보기
@Preview(showBackground = true)
@Composable
fun ShapeScreenPreview() {
    MyCityTheme {
        Surface {
            ShapeScreen(
                shapes = LocalShapeDataProvider.allShapeList,
                onClick = { }
            )
        }
    }
}

// ColorScreen 미리보기
@Preview(showBackground = true)
@Composable
fun ColorScreenPreview() {
    MyCityTheme {
        Surface {
            ColorScreen(
                shapeWithColors = LocalShapeWithColorDataProvider.allCircleList,
                onClick = { }
            )
        }
    }
}

// DetailScreen 미리보기
@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    MyCityTheme {
        Surface {
            DetailScreen(
                shapeWithColor = LocalShapeWithColorDataProvider.defaultShapeWithColor
            )
        }
    }
}