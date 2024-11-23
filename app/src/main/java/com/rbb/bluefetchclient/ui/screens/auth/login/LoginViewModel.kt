package com.rbb.bluefetchclient.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbb.bluefetchclient.data.AuthRepository
import com.rbb.bluefetchclient.domain.Credentials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _error = MutableStateFlow<String?>("")
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onUsernameChange(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onLoginClick(onNavigateToHome: () -> Unit) {
        _isLoading.value = true

        viewModelScope.launch {
            authRepository.login(Credentials("test1", "pass1")).collect { result ->
                result.onSuccess {
                    onNavigateToHome()
                }.onFailure { exception ->
                    _error.emit(exception.message)
                }
                _isLoading.value = false
            }
        }
    }
}
