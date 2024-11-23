package com.rbb.bluefetchclient.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rbb.bluefetchclient.domain.Credentials
import com.rbb.bluefetchclient.network.ApiService
import com.rbb.bluefetchclient.network.CreateAccountRequest
import com.rbb.bluefetchclient.network.CreateAccountResponse
import com.rbb.bluefetchclient.network.ErrorResponse
import com.rbb.bluefetchclient.network.ErrorResponseParser
import com.rbb.bluefetchclient.network.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import javax.inject.Inject

interface AuthRepository {
    suspend fun createAccount(createAccountRequest: CreateAccountRequest): Flow<Result<CreateAccountResponse>>
    suspend fun login(credentials: Credentials): Flow<Result<LoginResponse>>
    suspend fun saveToken(token: String)
    fun getToken(): Flow<String?>
}

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {

    private val TOKEN_KEY = stringPreferencesKey("auth_token")

    override suspend fun login(credentials: Credentials): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.login(credentials)
            saveToken(response.token)
            emit(Result.success(response))
        } catch (e: HttpException) {
            val errorMessage = ErrorResponseParser.parseErrorResponse(e)
            emit(Result.failure(Exception(errorMessage)))
        } catch (exception: Exception) {
            emit(Result.failure(exception))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    override suspend fun createAccount(
        createAccountRequest: CreateAccountRequest
    ): Flow<Result<CreateAccountResponse>> = flow {
        try {
            val response = apiService.createAccount(createAccountRequest)
            saveToken(response.token)
            emit(Result.success(response))
        } catch (e: HttpException) {
            val errorMessage = ErrorResponseParser.parseErrorResponse(e)
            emit(Result.failure(Exception(errorMessage)))
        } catch (exception: Exception) {
            emit(Result.failure(exception))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }

    override suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    override fun getToken(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }
}
