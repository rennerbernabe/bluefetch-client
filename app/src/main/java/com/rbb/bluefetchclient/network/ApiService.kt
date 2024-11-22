package com.rbb.bluefetchclient.network

import com.rbb.bluefetchclient.domain.Credentials
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/account/login")
    suspend fun login(@Body credentials: Credentials): LoginResponse
}