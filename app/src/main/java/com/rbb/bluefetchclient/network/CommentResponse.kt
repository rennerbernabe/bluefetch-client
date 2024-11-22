package com.rbb.bluefetchclient.network

import com.rbb.bluefetchclient.data.CommentIdSerializer
import com.rbb.bluefetchclient.domain.Comment
import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(
    @Serializable(with = CommentIdSerializer::class)
    val id: String,
    val text: String,
    val timestamp: Long? = null,
    val updatedAt: String? = null,
    val createdAt: String? = null,
    val username: String? = null
)

fun CommentResponse.toDomain() = Comment(
    id = id,
    text = text,
    timestamp = timestamp,
    updatedAt = updatedAt,
    createdAt = createdAt,
    username = username
)
