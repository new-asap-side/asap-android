package com.asap.domain.usecase

import com.asap.domain.repository.UserRepository
import com.kakao.sdk.auth.model.OAuthToken
import javax.inject.Inject


interface KakaoLoginUseCase {
    suspend operator fun invoke(token: OAuthToken)
}

class KakaoLoginUseCaseImpl @Inject constructor(
    private val repository: UserRepository
) : KakaoLoginUseCase {
    override suspend fun invoke(token: OAuthToken) = repository.cacheKakaoUserInfo(token)
}