package com.rbb.bluefetchclient.domain

data class Comment(
    val id: String,
    val text: String,
    val timestamp: Long?,
    val updatedAt: String?,
    val createdAt: String?,
    val username: String?
)
