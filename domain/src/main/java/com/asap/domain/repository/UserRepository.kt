package com.asap.domain.repository

import com.asap.domain.entity.ResultCard
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun kakaoLogin()

    suspend fun fetchResultCardData(): Flow<ResultCard?>
}