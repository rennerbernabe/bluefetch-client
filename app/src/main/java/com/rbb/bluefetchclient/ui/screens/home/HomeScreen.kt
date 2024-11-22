package com.rbb.bluefetchclient.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rbb.bluefetchclient.domain.Feed
import com.rbb.bluefetchclient.domain.User

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val feed by viewModel.feed.collectAsState()
    val loading by viewModel.loadingState.collectAsState()
    val error by viewModel.errorState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (loading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text(
                text = error ?: "An unknown error occurred",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        } else {
            FeedList(feed)
        }
    }
}

@Composable
fun FeedList(feedItems: List<Feed>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(feedItems) { feedItem ->
            FeedItem(feedItem)
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}

@Composable
fun FeedItem(
    feedItem: Feed,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = feedItem.user.profilePic,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = feedItem.user.username,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = feedItem.text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FeedItemPreview() {
    val sampleFeedItem = Feed(
        createdAt = "Fri, 04 Oct 2024 07:54:04 GMT",
        id = "-O8LWNW6lSPrfk00t_kB",
        text = "winter",
        updatedAt = "Fri, 04 Oct 2024 07:54:04 GMT",
        username = "stepcurr",
        user = User(
            username = "stepcurr",
            firstName = "Step",
            lastName = "Curr",
            profilePic = "https://storage.googleapis.com/bluefletch-learning-assignment.appspot.com/profilepics/bfdefault.png"
        )
    )

    FeedItem(feedItem = sampleFeedItem)
}
