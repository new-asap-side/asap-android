package com.asap.domain.usecase.user

import com.asap.domain.entity.remote.AuthKakaoResponse
import com.asap.domain.repository.UserRepository
import javax.inject.Inject

interface CacheUserUseCase {
    suspend operator fun invoke(response: AuthKakaoResponse)
}

class CacheUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : CacheUserUseCase {
    override suspend fun invoke(response: AuthKakaoResponse) {
        userRepository.cacheKakaoUserInfo(response)
    }
}