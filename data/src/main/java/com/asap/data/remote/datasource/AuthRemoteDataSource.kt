package com.asap.data.remote.datasource

import com.asap.data.remote.request.PatchAlarmTokenBody
import com.asap.data.remote.service.AuthService
import com.asap.domain.entity.remote.AuthKakaoBody
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.entity.remote.auth.AuthKakaoResponse
import com.asap.domain.entity.remote.auth.AuthResponse
import com.asap.domain.entity.remote.auth.RefreshTokenResponse
import com.asap.domain.entity.remote.auth.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun kakaoLogin(kakaoAccessToken: String): AuthKakaoResponse? {
        return AuthKakaoBody(
            kakaoAccessToken = kakaoAccessToken,
            alarmToken = TokenManager.fcmToken
        ).let { body ->
            authService.authKakao(body)
        }.let { response ->
            if (!response.isSuccessful) throw HttpException(response)
            response.body()
        }
    }

    suspend fun authKakao(kakaoAccessToken: String): Flow<AuthResponse?> = flow {
        AuthKakaoBody(
            kakaoAccessToken = kakaoAccessToken,
            alarmToken = TokenManager.fcmToken
        ).let { body ->
            authService.authKakao(body)
        }.also { response ->
            if (!response.isSuccessful) throw HttpException(response)
            emit(response.body())
        }
    }

    suspend fun refreshToken(token: String): RefreshTokenResponse? {
        val header = "Bearer $token"

        val response = authService.refreshToken(refreshToken = header)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
        return response.body()
    }
}