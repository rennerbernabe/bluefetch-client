package com.rbb.bluefetchclient.ui.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbb.bluefetchclient.data.AuthRepository
import com.rbb.bluefetchclient.network.CreateAccountRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onUsernameChange(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onFirstnameChange(newFirstname: String) {
        _uiState.value = _uiState.value.copy(firstName = newFirstname)
    }

    fun onLastnameChange(newLastname: String) {
        _uiState.value = _uiState.value.copy(lastName = newLastname)
    }

    fun onRegisterClick(onNavigateToHome: () -> Unit) {
        val currentState = _uiState.value

        val validationResult = validateInput(
            firstName = currentState.firstName,
            lastName = currentState.lastName,
            username = currentState.username,
            password = currentState.password
        )

        if (validationResult != null) {
            _errorMessage.value = validationResult
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            authRepository.createAccount(
                CreateAccountRequest(
                    username = currentState.username,
                    password = currentState.password,
                    firstname = currentState.firstName,
                    lastname = currentState.lastName,
                )
            ).collect { result ->
                result.onSuccess {
                    onNavigateToHome()
                }.onFailure { exception ->
                    _errorMessage.value = exception.message ?: "An unknown error occurred"
                }
                _isLoading.value = false
            }
        }
    }

    private fun validateInput(
        firstName: String,
        lastName: String,
        username: String,
        password: String
    ): String? {
        return when {
            firstName.isBlank() -> "First name is required."
            lastName.isBlank() -> "Last name is required."
            username.isBlank() -> "Username is required."
            password.isBlank() -> "Password is required."
            password.length < 5 -> "Password must be at least 5 characters long."
            else -> null
        }
    }
}
