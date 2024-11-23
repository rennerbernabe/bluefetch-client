package com.rbb.bluefetchclient.data

import com.rbb.bluefetchclient.domain.Feed
import com.rbb.bluefetchclient.domain.toDate
import com.rbb.bluefetchclient.network.ApiService
import com.rbb.bluefetchclient.network.ErrorResponseParser
import com.rbb.bluefetchclient.network.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
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
            val sortedFeed = response
                .map { it.toDomain() }
                .sortedByDescending { it.toDate() }
            emit(Result.success(sortedFeed))
        } catch (e: HttpException) {
            val errorMessage = ErrorResponseParser.parseErrorResponse(e)
            emit(Result.failure(Exception(errorMessage)))
        } catch (e: SerializationException) {
            emit(Result.failure(Exception("Parsing error: ${e.localizedMessage}")))
        } catch (exception: Exception) {
            emit(Result.failure(exception))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }
}
