package com.rbb.bluefetchclient.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val dataStore: DataStore<Preferences>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStore.data.map { preferences -> preferences[stringPreferencesKey("auth_token")] }
                .firstOrNull()
        }
        val newRequest = chain.request().newBuilder()
            .apply {
                token?.let { addHeader("Authorization", "Bearer $it") }
            }
            .build()
        return chain.proceed(newRequest)
    }
}