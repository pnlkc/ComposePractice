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
    // ????????? ??? ?????? ?????? ?????? ??????
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(12.dp),
        // ?????? ????????? ?????? ?????? ???????????? ??????
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, Color.Gray),
        elevation = 10.dp
    ) {
        Column(
            modifier = modifier
                .background(MaterialTheme.colors.secondary)
                // ????????? ?????? ???????????? ??????????????? ??????
                .clickable(onClick = { expanded = !expanded })
                // ??????????????? ???????????? ??????????????? ??? ?????????????????? ?????? ??????
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .padding(16.dp),
            // ?????? ????????? ????????? ?????? ?????? ???????????? ??????
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(id = colorData.title),
                style = MaterialTheme.typography.h6,
                // ????????? ?????? ???????????? ??????
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
                    // ???????????? ??? ?????? ???????????? ?????? (MaterialTheme.typography.body2 => 14.sp)
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