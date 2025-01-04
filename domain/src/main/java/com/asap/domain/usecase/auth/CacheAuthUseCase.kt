package com.asap.domain.usecase.auth

import com.asap.domain.entity.remote.auth.AuthResponse
import com.asap.domain.repository.AuthRepository
import javax.inject.Inject

interface CacheAuthUseCase {
    suspend operator fun invoke(response: AuthResponse): Boolean
}

class CacheAuthKakaoUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
): CacheAuthUseCase {
    override suspend fun invoke(response: AuthResponse): Boolean {
        try {
            authRepository.cacheKakaoAuth(response)
            return true
        } catch (e: Exception) {
            return false
        }
    }

}