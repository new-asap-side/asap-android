package com.asap.domain.usecase.auth

import com.asap.domain.entity.remote.auth.AuthResponse
import com.asap.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AuthKakaoUseCase {
    suspend operator fun invoke(kakaoAccessToken: String): Flow<AuthResponse?>
}

class AuthKakaoUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
): AuthKakaoUseCase {
    override suspend fun invoke(kakaoAccessToken: String): Flow<AuthResponse?> {
        return authRepository.authKakao(kakaoAccessToken = kakaoAccessToken)
    }
}