package com.asap.domain.repository

import com.asap.domain.entity.KakaoLoginResponse
import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.local.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun kakaoLogin(): Flow<KakaoLoginResponse?>

    suspend fun cacheUserInfo(user: User)

    suspend fun fetchResultCardData(): Flow<ResultCard?>
}