package com.asap.domain.usecase.group

import com.asap.domain.entity.remote.MyRanking
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MyRankingUseCase {
    suspend operator fun invoke(): Flow<List<MyRanking>?>
}

class MyRankingUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : MyRankingUseCase {
    override suspend fun invoke(): Flow<List<MyRanking>?> {
        return groupRepository.fetchMyRanking()
    }
}