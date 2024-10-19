package com.asap.domain.repository

import com.asap.domain.entity.ResultCard
import retrofit2.Response

interface UserInfoRepository {
    suspend fun kakaoLogin(): Response<Unit>

    suspend fun fetchResultCardData(): Response<ResultCard>
}