package com.asap.domain.repository

import com.asap.domain.entity.ResultCard
import retrofit2.Response

interface UserRepository {
    suspend fun kakaoLogin(): Response<Unit>

    suspend fun fetchResultCardData(): Response<ResultCard>
}