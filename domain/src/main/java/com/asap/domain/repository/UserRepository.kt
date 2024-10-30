package com.asap.domain.repository

import com.asap.domain.entity.ResultCard
import com.kakao.sdk.auth.model.OAuthToken
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun isCached(): Boolean

    suspend fun cacheKakaoUserInfo(token: OAuthToken)

    suspend fun fetchResultCardData(): Flow<ResultCard?>
}