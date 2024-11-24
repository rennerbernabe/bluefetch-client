package com.rbb.bluefetchclient.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rbb.bluefetchclient.R
import com.rbb.bluefetchclient.domain.Feed
import com.rbb.bluefetchclient.domain.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val feed by viewModel.feed.collectAsState()
    val loading by viewModel.loadingState.collectAsState()
    val error by viewModel.errorState.collectAsState()

    var showFeedLimitSheet by remember { mutableStateOf(false) }
    val feedLimitSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }
    )

    var showUserFilterDialog by remember { mutableStateOf(false) }
    val userFilterSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                actions = {
                    IconButton(
                        onClick = {
                            showUserFilterDialog = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_person_search_24),
                            contentDescription = "Filter posts by user"
                        )
                    }

                    IconButton(
                        onClick = {
                            showFeedLimitSheet = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.filter_list_24),
                            contentDescription = "Limit posts"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
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
    )

    if (showFeedLimitSheet) {
        ModalBottomSheet(
            sheetState = feedLimitSheetState,
            onDismissRequest = {
                showFeedLimitSheet = false
            }
        ) {
            FeedLimitSheet(
                onLimitSelected = { limit ->
                    showFeedLimitSheet = false
                    viewModel.setPostLimit(limit)
                }
            )
        }
    }

    if (showUserFilterDialog) {
        ModalBottomSheet(
            sheetState = userFilterSheetState,
            onDismissRequest = {
                showUserFilterDialog = false
            }
        ) {
            UserFilterBottomSheet(
                onFilterSelected = { username ->
                    showUserFilterDialog = false
                    viewModel.filterFeedByUser(username)
                }
            )
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
                style = MaterialTheme.typography.titleLarge,
                color = colorResource(R.color.blue_1)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = feedItem.text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun FeedLimitSheet(onLimitSelected: (Int) -> Unit) {
    val limits = listOf(5, 10, 15, 20)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Select post limit",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        limits.forEach { limit ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onLimitSelected(limit)
                    }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$limit posts",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
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
        user = User(
            username = "stepcurr",
            firstName = "Step",
            lastName = "Curr",
            profilePic = "https://storage.googleapis.com/bluefletch-learning-assignment.appspot.com/profilepics/bfdefault.png"
        )
    )

    FeedItem(feedItem = sampleFeedItem)
}
