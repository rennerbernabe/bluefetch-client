package com.rbb.bluefetchclient.ui.screens.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rbb.bluefetchclient.R

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Registration",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        OutlinedTextField(
            value = state.firstName,
            onValueChange = { viewModel.onFirstnameChange(it) },
            label = { Text(stringResource(R.string.first_name)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.lastName,
            onValueChange = { viewModel.onLastnameChange(it) },
            label = { Text(stringResource(R.string.last_name)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.username,
            onValueChange = { viewModel.onUsernameChange(it) },
            label = { Text(stringResource(R.string.username)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onNavigateToLogin() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = colorResource(R.color.blue_1),
                    containerColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.sign_in))
            }
            Button(
                onClick = { viewModel.onRegisterClick(onNavigateToHome) },
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = stringResource(R.string.sign_up))
                }
            }
        }
    }
}
