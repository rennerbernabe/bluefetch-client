package com.rbb.bluefetchclient.network

import com.rbb.bluefetchclient.data.AuthRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

class AuthInterceptor @Inject constructor(
    private val authRepositoryProvider: Provider<AuthRepository>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = runBlocking {
            authRepositoryProvider.get().getToken().firstOrNull()
        }

        val request = chain.request().newBuilder().apply {
            token?.let {
                addHeader("authorization", "$token")
            }
        }.build()

        return chain.proceed(request)
    }
}