package com.rbb.bluefetchclient.data

import com.rbb.bluefetchclient.domain.Feed
import com.rbb.bluefetchclient.network.ApiService
import com.rbb.bluefetchclient.network.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException
import javax.inject.Inject

interface FeedRepository {
    fun getFeed(limit: Int): Flow<Result<List<Feed>>>
}

class FeedRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : FeedRepository {

    override fun getFeed(limit: Int): Flow<Result<List<Feed>>> = flow {
        try {
            val response = apiService.getFeed(limit)
            val domainFeed = response.map { it.toDomain() }
            emit(Result.success(domainFeed))
        } catch (e: SerializationException) {
            emit(Result.failure(Exception("Parsing error: ${e.localizedMessage}")))
        } catch (exception: Exception) {
            emit(Result.failure(exception))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }
}
