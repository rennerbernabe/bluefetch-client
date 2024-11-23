package com.rbb.bluefetchclient.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbb.bluefetchclient.data.FeedRepository
import com.rbb.bluefetchclient.domain.Feed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {

    private val _feed = MutableStateFlow<List<Feed>>(emptyList())
    val feed: StateFlow<List<Feed>> = _feed

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    private var fullFeed: List<Feed> = emptyList()

    init {
        loadFeed()
    }

    private fun loadFeed(limit: Int = 25) {
        viewModelScope.launch {
            _loadingState.emit(true)
            feedRepository.getFeed(limit).collect { result ->
                result.onSuccess { feedItems ->
                    fullFeed = feedItems
                    _feed.value = feedItems
                }.onFailure { exception ->
                    _errorState.value = exception.message ?: "An unknown error occurred"
                }
                _loadingState.value = false
            }
        }
    }

    fun setPostLimit(limit: Int) {
        _feed.value = fullFeed.take(limit)
    }

    fun filterFeedByUser(username: String?) {
        _feed.value = if (username.isNullOrEmpty()) {
            fullFeed
        } else {
            fullFeed.filter { it.user.username == username }
        }
    }
}
