package com.asap.data.repository

import com.asap.domain.entity.ResultCard
import com.asap.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {
    override suspend fun kakaoLogin(): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchResultCardData(): Response<ResultCard> {
        TODO("Not yet implemented")
    }
}