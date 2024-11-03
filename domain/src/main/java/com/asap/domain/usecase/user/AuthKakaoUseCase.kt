package com.asap.domain.usecase.user

import com.asap.domain.entity.remote.AuthKakaoResponse
import com.asap.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface AuthKakaoUseCase {
    suspend operator fun invoke(kakaoAccessToken: String): Flow<AuthKakaoResponse?>
}

class AuthKakaoUseCaseImpl @Inject constructor(
    private val repository: UserRepository
) : AuthKakaoUseCase {
    override suspend fun invoke(kakaoAccessToken: String) = repository.authKakao(kakaoAccessToken)
}