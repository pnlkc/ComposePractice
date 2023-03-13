package com.example.ch5_2_4_amphibians.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ch5_2_4_amphibians.R
import com.example.ch5_2_4_amphibians.network.AmphibiansData
import com.example.ch5_2_4_amphibians.ui.theme.Compose_PracticeTheme
import com.example.ch5_2_4_amphibians.ui.theme.Typography

@Composable
fun HomeScreen(
    amphibiansUiState: AmphibiansUiState,
    modifier: Modifier = Modifier,
) {
    when (amphibiansUiState) {
        is AmphibiansUiState.Loading -> LoadingScreen(modifier)
        is AmphibiansUiState.Success -> AmphibiansLazyColumn(amphibiansData = amphibiansUiState.data)
        is AmphibiansUiState.Error -> ErrorScreen(modifier)
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(stringResource(R.string.loading_failed))
    }
}

@Composable
fun AmphibiansCard(
    amphibiansData: AmphibiansData,
    modifier: Modifier,
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
    ) {
        Column {
            Text(
                text = amphibiansData.name + " (${amphibiansData.type})",
                style = Typography.body2,
                modifier = modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Box(modifier = modifier.size(12.dp))

            Text(
                text = amphibiansData.description,
                style = Typography.body1,
                modifier = modifier.padding(horizontal = 16.dp)
            )

            Box(modifier = modifier.size(6.dp))

            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(amphibiansData.imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                // 이미지 로딩 에러시 보일 이미지
                error = painterResource(id = R.drawable.ic_broken_image),
                // 이미지 로딩중일 때 보일 이미지
                placeholder = painterResource(id = R.drawable.loading_img),
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AmphibiansLazyColumn(
    amphibiansData: List<AmphibiansData>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(
            items = amphibiansData
        ) { amphibians ->
            AmphibiansCard(
                amphibiansData = amphibians,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AmphibiansCardPreview() {
    Compose_PracticeTheme {
        AmphibiansCard(
            amphibiansData = AmphibiansData("test", "type test", "description test", ""),
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AmphibiansLazyColumnPreview() {
    Compose_PracticeTheme {
        AmphibiansLazyColumn(
            amphibiansData = List(10) {
                AmphibiansData(
                    "test $it",
                    "type test $it",
                    "description test $it",
                    ""
                )
            },
            modifier = Modifier
        )
    }
}
