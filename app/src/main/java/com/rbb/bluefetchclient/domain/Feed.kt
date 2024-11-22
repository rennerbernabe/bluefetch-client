package com.rbb.bluefetchclient.domain

import java.text.SimpleDateFormat
import java.util.Locale

data class Feed(
    val id: String,
    val text: String,
    val createdAt: String,
    val updatedAt: String,
    val username: String,
    val user: User
)

fun Feed.toDate(): Long {
    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    return try {
        dateFormat.parse(this.createdAt)?.time ?: 0L
    } catch (e: Exception) {
        0L
    }
}
