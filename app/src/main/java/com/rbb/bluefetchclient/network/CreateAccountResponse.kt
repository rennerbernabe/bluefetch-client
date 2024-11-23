package com.rbb.bluefetchclient.network

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountResponse(
    val token: String
)
