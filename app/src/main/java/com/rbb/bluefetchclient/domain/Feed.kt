package com.rbb.bluefetchclient.domain

data class Feed(
    val id: String,
    val text: String,
    val createdAt: String,
    val updatedAt: String,
    val username: String,
    val user: User
)
