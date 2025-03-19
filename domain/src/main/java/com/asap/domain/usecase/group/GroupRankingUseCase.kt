package com.asap.domain.usecase.group

import com.asap.domain.entity.remote.GroupRanking
import com.asap.domain.entity.remote.RankingNumberResponse
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class GroupRankingUseCase(
    val fetchGroupRankingUseCase: FetchGroupRankingUseCase,
    val fetchRankingNumberUseCase: FetchRankingNumberUseCase,
    val fetchTodayRankingUseCase: FetchTodayRankingUseCase
)

// 그룹 누적 랭킹 조회
interface FetchGroupRankingUseCase {
    suspend operator fun invoke(groupId: Int): Flow<List<GroupRanking>?>
}

// 그룹 알람 해제 랭킹 조회
interface FetchRankingNumberUseCase {
    suspend operator fun invoke(groupId: Int): Flow<RankingNumberResponse?>
}

// 그룹 일일 랭킹 조회
interface FetchTodayRankingUseCase {
    suspend operator fun invoke(groupId: Int): Flow<List<GroupRanking>?>
}

class FetchGroupRankingUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : FetchGroupRankingUseCase {

    override suspend fun invoke(groupId: Int): Flow<List<GroupRanking>?> =
        groupRepository.fetchGroupRanking(groupId = groupId)
}


class FetchRankingNumberUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : FetchRankingNumberUseCase {
    override suspend fun invoke(groupId: Int): Flow<RankingNumberResponse?> {
        return groupRepository.fetchRankingNumber(groupId = groupId)
    }
}

class FetchTodayRankingUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : FetchTodayRankingUseCase {
    override suspend fun invoke(groupId: Int): Flow<List<GroupRanking>?> {
        return groupRepository.fetchTodayRanking(groupId = groupId)
    }

}