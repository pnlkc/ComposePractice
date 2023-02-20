package com.example.ch3_2_affirmation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.affirmations.ui.theme.AffirmationsTheme
import com.example.affirmations.ui.theme.Typography
import com.example.ch3_2_affirmation.data.PracticeDataSource
import com.example.ch3_2_affirmation.model.Topic

class PracticeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AffirmationsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PracticeApp()
                }
            }
        }
    }
}

@Composable
fun PracticeApp() {
    AffirmationsTheme {
        PracticeGrid(list = PracticeDataSource.topics)
    }
}

@Composable
fun PracticeCard(topic: Topic, modifier: Modifier = Modifier) {
    Card(elevation = 4.dp) {
        Row {
            Image(
                painter = painterResource(topic.imageId),
                contentDescription = stringResource(topic.stringId),
                modifier = modifier.size(width = 68.dp, height = 68.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)) {
                Text(
                    text = stringResource(topic.stringId),
                    style = Typography.body2,
                    modifier = modifier.padding(bottom = 8.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_grain),
                        contentDescription = "ic_grain icon",
                        modifier = modifier.size(12.dp)
                    )
                    Text(
                        text = topic.numId.toString(),
                        style = Typography.caption,
                        modifier = modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PracticeGrid(list: List<Topic>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        items(list) { topic ->
            PracticeCard(topic = topic)
        }
    }
}

@Preview
@Composable
fun PracticePreview() {
    AffirmationsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            PracticeApp()
        }
    }
}