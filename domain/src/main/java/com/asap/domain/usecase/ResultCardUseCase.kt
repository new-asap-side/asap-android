package com.asap.domain.usecase

import com.asap.domain.entity.ResultCard
import com.asap.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ResultCardUseCase {
    suspend operator fun invoke(): Flow<ResultCard?>
}

class ResultCardUseCaseImpl @Inject constructor(
    private val userInfoRepository: UserRepository
) : ResultCardUseCase {
    override suspend fun invoke(): Flow<ResultCard?> {
        return userInfoRepository.fetchResultCardData()
    }
}