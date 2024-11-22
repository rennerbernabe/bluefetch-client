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

    private val _error = MutableStateFlow<String?>("")
    val error: StateFlow<String?> = _error

    fun onUsernameChange(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onLoginClick() {
        viewModelScope.launch {
            authRepository.login(Credentials("test1", "pass1")).collect { result ->
                result.onSuccess {
                    //TODO handle success

                }.onFailure { exception ->
                    _error.value = exception.message
                }
            }
        }
    }
}
