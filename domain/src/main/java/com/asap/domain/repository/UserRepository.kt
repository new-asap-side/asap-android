package com.asap.domain.repository

import com.asap.domain.entity.KakaoLoginResponse
import com.asap.domain.entity.ResultCard
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun kakaoLogin(): Flow<KakaoLoginResponse?>

    suspend fun fetchResultCardData(): Flow<ResultCard?>
}