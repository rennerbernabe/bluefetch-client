package com.rbb.bluefetchclient.network

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountRequest(
    val username: String,
    val password: String,
    val firstname: String,
    val lastname: String
)
