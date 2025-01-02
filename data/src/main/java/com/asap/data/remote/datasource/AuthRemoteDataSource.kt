package com.asap.data.remote.datasource

import com.asap.data.remote.service.AuthService
import com.asap.domain.entity.remote.auth.AuthKakaoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun authKakao(kakaoAccessToken: String): Flow<AuthKakaoResponse?> = flow {
        val response = authService.authKakao(
            hashMapOf("kakaoAccessToken" to kakaoAccessToken)
        )
        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        emit(response.body())
    }
}