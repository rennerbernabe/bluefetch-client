package com.rbb.bluefetchclient.network

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException

object ErrorResponseParser {

    fun parseErrorResponse(exception: HttpException): String {
        return try {
            val errorBody = exception.response()?.errorBody()?.string()
            if (!errorBody.isNullOrEmpty()) {
                val errorResponse = Json.decodeFromString<ErrorResponse>(errorBody)
                errorResponse.message
            } else {
                "An unexpected error occurred: ${exception.message()}"
            }
        } catch (e: Exception) {
            "Failed to parse error response: ${e.localizedMessage}"
        }
    }
}