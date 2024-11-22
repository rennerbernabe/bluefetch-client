package com.rbb.bluefetchclient.network

import com.rbb.bluefetchclient.domain.Credentials
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/account/login")
    suspend fun login(@Body credentials: Credentials): LoginResponse

    @GET("/feed")
    suspend fun getFeed(@Query("limit") limit: Int): List<FeedResponse>
}
