package com.asap.domain.usecase.auth

import android.content.Context
import com.asap.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface KakaoLoginUseCase {
    suspend operator fun invoke(scope: CoroutineScope, context: Context): Flow<Boolean>
}

class KakaoLoginUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : KakaoLoginUseCase {
    override suspend fun invoke(scope: CoroutineScope, context: Context) =
        authRepository.kakaoLogin(scope, context)

}