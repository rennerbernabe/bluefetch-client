package com.rbb.bluefetchclient.network

import com.rbb.bluefetchclient.domain.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val username: String,
    val firstName: String,
    val lastName: String,
    val profilePic: String
)

fun UserResponse.toDomain() = User(
    username = username,
    firstName = firstName,
    lastName = lastName,
    profilePic = profilePic
)
