package com.example.ch3_3_superheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ch3_3_superheroes.model.Hero
import com.example.ch3_3_superheroes.model.HeroesRepository.heroes
import com.example.ch3_3_superheroes.ui.theme.SuperheroesTheme
import com.example.ch3_3_superheroes.ui.theme.Typography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperheroesTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
}

@Composable
fun SuperHeroApp() {
    Scaffold(
        topBar = { SuperHeroTopAppBar() }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(heroes) {hero ->
                SuperHeroCardItem(hero = hero)
            }
        }
    }
}

@Composable
fun SuperHeroCardItem(hero: Hero, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)),
        elevation = 2.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(72.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(id = hero.nameRes),
                    style = Typography.h3,
                )
                Text(
                    text = stringResource(id = hero.descriptionRes),
                    style = Typography.body1
                )
            }
            Image(
                painter = painterResource(id = hero.imageRes),
                contentDescription = null,
                modifier = modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

@Composable
fun SuperHeroTopAppBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Superheroes",
            style = Typography.h1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SuperheroesTheme(darkTheme = true) {
        SuperHeroApp()
    }
}