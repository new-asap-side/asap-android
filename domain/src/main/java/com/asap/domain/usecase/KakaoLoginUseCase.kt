package com.asap.domain.usecase

import com.asap.domain.entity.KakaoLoginResponse
import com.asap.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface KakaoLoginUseCase {
    suspend operator fun invoke(): Flow<KakaoLoginResponse?>
}

class KakaoLoginUseCaseImpl @Inject constructor(
    private val repository: UserRepository
) : KakaoLoginUseCase {
    override suspend fun invoke(): Flow<KakaoLoginResponse?> = repository.kakaoLogin()
}