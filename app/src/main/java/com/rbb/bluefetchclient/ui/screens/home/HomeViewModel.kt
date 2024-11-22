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

    val feed = MutableStateFlow<List<Feed>>(emptyList())
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    init {
        loadFeed()
    }

    private fun loadFeed(limit: Int = 25) {
        viewModelScope.launch {
            _loadingState.emit(true)
            feedRepository.getFeed(limit).collect { result ->
                result.onSuccess { domainFeed ->
                    feed.emit(domainFeed)
                }.onFailure { exception ->
                    _errorState.emit(exception.message)
                }
                _loadingState.emit(false)
            }
        }
    }
}
