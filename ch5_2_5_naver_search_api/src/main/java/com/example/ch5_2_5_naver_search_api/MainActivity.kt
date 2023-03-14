package com.example.ch5_2_5_naver_search_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ch5_2_5_naver_search_api.ui.NSIApp
import com.example.ch5_2_5_naver_search_api.ui.NSIViewModel
import com.example.ch5_2_5_naver_search_api.ui.theme.Compose_PracticeTheme

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
                    val viewModel: NSIViewModel = viewModel(factory = NSIViewModel.Factory)
                    NSIApp(nsiViewModel = viewModel)
                }
            }
        }
    }
}