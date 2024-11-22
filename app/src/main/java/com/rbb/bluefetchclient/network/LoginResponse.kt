package com.rbb.bluefetchclient.network

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String
)
