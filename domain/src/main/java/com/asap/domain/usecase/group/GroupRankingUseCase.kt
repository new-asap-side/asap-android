package com.asap.domain.usecase.group

import com.asap.domain.entity.remote.GroupRanking
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchGroupRankingUseCase {
    suspend operator fun invoke(groupId: Int): Flow<List<GroupRanking>?>
}

class FetchGroupRankingUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : FetchGroupRankingUseCase {
    override suspend fun invoke(groupId: Int): Flow<List<GroupRanking>?> =
        groupRepository.fetchGroupRanking()

}