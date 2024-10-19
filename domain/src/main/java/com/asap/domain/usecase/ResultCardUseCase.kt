package com.asap.domain.usecase

import com.asap.domain.entity.ResultCard
import com.asap.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

interface ResultCardUseCase {
    suspend operator fun invoke(): Response<ResultCard>
}

class ResultCardUseCaseImpl @Inject constructor(
    private val userInfoRepository: UserRepository
) : ResultCardUseCase {
    override suspend fun invoke(): Response<ResultCard> {
        return userInfoRepository.fetchResultCardData()
    }
}