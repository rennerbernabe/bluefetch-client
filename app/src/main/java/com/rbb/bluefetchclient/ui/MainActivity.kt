package com.rbb.bluefetchclient.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rbb.bluefetchclient.ui.screens.home.HomeScreen
import com.rbb.bluefetchclient.ui.screens.auth.login.LoginScreen
import com.rbb.bluefetchclient.ui.screens.auth.register.RegisterScreen
import com.rbb.bluefetchclient.ui.theme.BlueFetchClientTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlueFetchClientTheme(darkTheme = false) {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { LoginScreen(navController) }
                    composable("register") { RegisterScreen(navController) }
                    composable("feeds") { HomeScreen() }
                }
            }
        }
    }
}
