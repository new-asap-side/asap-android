package com.asap.data.remote

import android.util.Log
import com.asap.data.remote.service.UserService
import com.asap.domain.entity.ResultCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) {
    val resultCard: Flow<ResultCard?> = flow {
        val response = userService.fetchResultCard()
        Log.d("UserRemoteDataSource", "response: $response")
        emit(response.body())
    }
}