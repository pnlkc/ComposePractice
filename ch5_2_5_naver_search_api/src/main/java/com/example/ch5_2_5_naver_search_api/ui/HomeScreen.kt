package com.example.ch5_2_5_naver_search_api.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ch5_2_5_naver_search_api.R
import com.example.ch5_2_5_naver_search_api.network.Item
import com.example.ch5_2_5_naver_search_api.ui.theme.Compose_PracticeTheme

@Composable
fun HomeScreen(
    nsiViewModel: NSIViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = modifier
                .height(70.dp)
                .padding(5.dp)
        ) {
            OutlinedTextField(
                modifier = modifier.weight(1f),
                singleLine = true,
                value = nsiViewModel.textInput,
                onValueChange = { newText ->
                    nsiViewModel.updateText(newText)
                },
                placeholder = { Text(text = stringResource(id = R.string.outline_text_field_hint)) },
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_cancel_24),
                        contentDescription = null,
                        modifier = modifier.clickable {

                        }
                    )
                },
            )

            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = null,
                modifier = modifier
                    .clickable {
                        nsiViewModel.getSearchImage(
                            query = nsiViewModel.textInput,
                        )
                    }
                    .padding(5.dp)
                    .size(50.dp)
            )
        }

        when (val nsiUiState = nsiViewModel.nsiUiState) {
            is NSIUiState.Loading -> LoadingScreen(modifier)
            is NSIUiState.Success -> SuccessScreen(nsiUiState.data, modifier)
            is NSIUiState.Error -> ErrorScreen(modifier)
        }
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
fun SuccessScreen(
    data: List<Item>,
    modifier: Modifier = Modifier,
) {
    SearchResultGridScreen(data, modifier)
}

@Composable
fun SearchItemCard(
    item: Item,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(item.link)
                // 이미지 요청이 성공적으로 완료되면 크로스페이드 애니메이션과 함께 이미지가 보여짐
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            // 이미지 로딩 에러시 보일 이미지
            error = painterResource(id = R.drawable.ic_broken_image),
            // 이미지 로딩중일 때 보일 이미지
            placeholder = painterResource(id = R.drawable.loading_img)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResultGridScreen(
    data: List<Item>,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(
            items = data,
            key = { photo -> photo.title }
        ) { item ->
            SearchItemCard(item = item, modifier)
        }
    }
}
