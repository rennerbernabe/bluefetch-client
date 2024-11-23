package com.rbb.bluefetchclient.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbb.bluefetchclient.data.AuthRepository
import com.rbb.bluefetchclient.domain.Credentials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _error = MutableStateFlow<String?>(null)
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
        val username = _uiState.value.username.trim()
        val password = _uiState.value.password.trim()

        val validationError = validateInput(username, password)
        if (validationError != null) {
            _error.value = validationError
            return
        }

        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            authRepository.login(Credentials(username, password)).collect { result ->
                result.onSuccess {
                    onNavigateToHome()
                }.onFailure { exception ->
                    _error.value = exception.message ?: "An unknown error occurred"
                }
                _isLoading.value = false
            }
        }
    }

    private fun validateInput(username: String, password: String): String? {
        return when {
            username.isBlank() -> "Username is required."
            password.isBlank() -> "Password is required."
            password.length < 5 -> "Password must be at least 5 characters long."
            else -> null
        }
    }
}
