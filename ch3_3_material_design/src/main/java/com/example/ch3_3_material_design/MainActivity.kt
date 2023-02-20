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
package com.example.ch3_3_material_design

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ch3_3_material_design.data.Dog
import com.example.ch3_3_material_design.data.dogs
import com.example.ch3_3_material_design.ui.theme.Green50
import com.example.ch3_3_material_design.ui.theme.White
import com.example.ch3_3_material_design.ui.theme.WoofTheme

// 상태 표시줄 색상은 res -> values 에서 설정 가능
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoofTheme {
                WoofApp()
            }
        }
    }
}

/**
 * Composable that displays an app bar and a list of dogs.
 */
@Composable
fun WoofApp() {
    // Compose 1.2.0 부터 Scaffold 사용시 padding 값을 지정하지 않으면
    // "UnusedMaterialScaffoldPaddingParameter" 에러 발생
    Scaffold(
        topBar = {
            WoofTopAppBar()
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(it)
        ) {
            items(dogs) {
                DogItem(dog = it)
            }
        }
    }
}

/**
 * Composable that displays a list item containing a dog icon and their information.
 *
 * @param dog contains the data that populates the list item
 * @param modifier modifiers to set to this composable
 */
@Composable
fun DogItem(dog: Dog, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (expanded) White else MaterialTheme.colors.surface
    )

    // shape는 중형(medium) 구성요소로 Shape의 medium 속성의 영향을 받음
    Card(
        modifier = modifier.padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .background(color = color)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                DogIcon(dog.imageResourceId)
                DogInformation(dog.name, dog.age)
                // DogIcon, DogInformation와 DogItemButton 사이에 빈공간을 채워주는 역할
                Spacer(modifier = modifier.weight(1f))
                DogItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }
            if (expanded) {
                DogHobby(dogHobby = dog.hobbies)
            }
        }
    }
}

@Composable
private fun DogItemButton(
    expanded: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (!expanded) Icons.Filled.ExpandMore else Icons.Filled.ExpandLess,
            tint = MaterialTheme.colors.secondary,
            contentDescription = stringResource(id = R.string.expand_button_content_description)
        )
    }
}

/**
 * Composable that displays a photo of a dog.
 *
 * @param dogIcon is the resource ID for the image of the dog
 * @param modifier modifiers to set to this composable
 */
@Composable
fun DogIcon(@DrawableRes dogIcon: Int, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .size(64.dp)
            .padding(8.dp)
            // 모서리에 라운드를 주는 속성
            .clip(RoundedCornerShape(50)),

        // 이미지가 도형에 맞게 잘리게 하는 속성
        contentScale = ContentScale.Crop,
        painter = painterResource(dogIcon),
        contentDescription = null
    )
}

/**
 * Composable that displays a dog's name and age.
 *
 * @param dogName is the resource ID for the string of the dog's name
 * @param dogAge is the Int that represents the dog's age
 * @param modifier modifiers to set to this composable
 */
@Composable
fun DogInformation(@StringRes dogName: Int, dogAge: Int, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = stringResource(dogName),
            style = MaterialTheme.typography.h2,
            modifier = modifier.padding(top = 8.dp)
        )
        Text(
            text = stringResource(R.string.years_old, dogAge),
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun DogHobby(
    @StringRes dogHobby: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(
        start = 16.dp,
        top = 8.dp,
        bottom = 16.dp,
        end = 16.dp
    )
    ) {
        Text(
            text = stringResource(id = R.string.about),
            style = MaterialTheme.typography.h3
        )
        Text(
            text = stringResource(id = dogHobby),
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun WoofTopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = modifier
                .size(64.dp)
                .padding(8.dp),
            painter = painterResource(id = R.drawable.ic_woof_logo),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h1
        )
    }
}

/**
 * Composable that displays what the UI of the app looks like in light theme in the design tab.
 */
@Preview
@Composable
fun WoofLightPreview() {
    WoofTheme(darkTheme = false) {
        WoofApp()
    }
}

@Preview
@Composable
fun WoofDarkPreview() {
    WoofTheme(darkTheme = true) {
        WoofApp()
    }
}

