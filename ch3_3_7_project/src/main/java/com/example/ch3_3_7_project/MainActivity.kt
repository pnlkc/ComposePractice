package com.example.ch3_3_7_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ch3_3_7_project.data.ColorData
import com.example.ch3_3_7_project.data.colorList
import com.example.ch3_3_7_project.ui.theme.Compose_PracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_PracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ColorMeaningApp()
                }
            }
        }
    }
}

@Composable
fun ColorMeaningApp() {
    Scaffold(
        topBar = { ColorMeaningTopAppBar() }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(colorList) { colorData ->
                ColorItem(colorData = colorData)
            }
        }
    }
}

@Composable
fun ColorItem(colorData: ColorData, modifier: Modifier = Modifier) {
    // 클릭할 때 값이 변경 되는 변수
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(12.dp),
        // 카드 모서리 둥근 정도 조절하는 기능
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, Color.Gray),
        elevation = 10.dp
    ) {
        Column(
            modifier = modifier
                .background(MaterialTheme.colors.secondary)
                // 카드를 클릭 가능하게 만들어주는 기능
                .clickable(onClick = { expanded = !expanded })
                // 컴포저블의 사이즈가 변경되었을 때 애니메이션을 주는 기능
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .padding(16.dp),
            // 하위 객체들 사이의 수평 간격 조절하는 기능
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(id = colorData.title),
                style = MaterialTheme.typography.h6,
                // 텍스트 색상 변경하는 기능
                color = Color.Gray
            )

            Text(
                text = stringResource(id = colorData.name),
                style = MaterialTheme.typography.body1
            )

            Image(
                modifier = modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = colorData.image),
                contentDescription = stringResource(id = colorData.name)
            )

            if (expanded) {
                Text(
                    text = stringResource(id = colorData.description),
                    style = MaterialTheme.typography.body2,
                    // 텍스트의 줄 간격 수정하는 기능 (MaterialTheme.typography.body2 => 14.sp)
                    lineHeight = 25.sp
                )
            }
        }
    }
}

@Composable
fun ColorMeaningTopAppBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onPrimary
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_PracticeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ColorMeaningApp()
        }
    }
}