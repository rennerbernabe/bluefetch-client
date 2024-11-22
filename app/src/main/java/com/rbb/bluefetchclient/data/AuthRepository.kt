package com.rbb.bluefetchclient.data

import com.rbb.bluefetchclient.domain.Credentials
import com.rbb.bluefetchclient.network.ApiService
import com.rbb.bluefetchclient.network.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(credentials: Credentials): Flow<Result<LoginResponse>>
}

class AuthRepositoryImpl @Inject constructor(private val apiService: ApiService) : AuthRepository {

    override suspend fun login(credentials: Credentials): Flow<Result<LoginResponse>> = flow {
        val response = apiService.login(Credentials("test1", "pass1"))
        emit(Result.success(response))
    }.catch { exception ->
        emit(Result.failure(exception))
    }
}