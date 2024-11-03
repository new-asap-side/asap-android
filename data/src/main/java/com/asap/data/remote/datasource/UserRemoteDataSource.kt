package com.asap.data.remote.datasource

import com.asap.data.remote.service.UserService
import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.remote.AuthKakaoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) {
    val resultCard: Flow<ResultCard?> = flow {
        val response = userService.fetchResultCard()
        emit(response.body())
    }

    suspend fun authKakao(kakaoAccessToken: String): Flow<AuthKakaoResponse?> = flow {
        val response = userService.authKakao(
            hashMapOf("kakaoAccessToken" to kakaoAccessToken)
        )
        emit(response.body())
    }
}