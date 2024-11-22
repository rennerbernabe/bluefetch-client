package com.rbb.bluefetchclient.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val feed by viewModel.feed.collectAsState()
    val loading by viewModel.loadingState.collectAsState()
    val error by viewModel.errorState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (loading) {
            CircularProgressIndicator()
        } else if (error != null) { // error
            Text(
                text = error ?: "An unknown error occurred",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn {
                items(feed) { post ->
                    Text(post.text)
                }
            }
        }
    }
}
