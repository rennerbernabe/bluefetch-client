package com.rbb.bluefetchclient.network

import com.rbb.bluefetchclient.domain.Feed
import kotlinx.serialization.Serializable

@Serializable
data class FeedResponse(
    val id: String,
    val text: String,
    val createdAt: String,
    val updatedAt: String,
    val username: String,
    val user: UserResponse
)

fun FeedResponse.toDomain() = Feed(
    id = id,
    text = text,
    createdAt = createdAt,
    updatedAt = updatedAt,
    username = username,
    user = user.toDomain()
)
