package com.rbb.bluefetchclient.ui.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

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

    fun onRegisterClick() {
        viewModelScope.launch {
//            repository.registerUser(
//                uiState.username,
//                uiState.password,
//                uiState.firstname,
//                uiState.lastname
//            )
        }
    }
}
