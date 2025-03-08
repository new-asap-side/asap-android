package com.asap.domain.usecase.group

import com.asap.domain.entity.local.SearchEntity
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class FetchGroupUseCase(
    val searchGroupUseCase: SearchGroupUseCase,
    val getSearchedListUseCase: GetSearchedListUseCase
)

// 알람 그룹 검색
interface SearchGroupUseCase {
    suspend operator fun invoke(query: String): Flow<List<AlarmGroup>>
}

// 알람 그룹 검색 기록 조회
interface GetSearchedListUseCase {
    suspend operator fun invoke(): List<SearchEntity>
}

class SearchGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : SearchGroupUseCase {
    override suspend fun invoke(query: String) = groupRepository.searchGroup(query)
}

class GetSearchedListUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : GetSearchedListUseCase {
    override suspend fun invoke(): List<SearchEntity> = groupRepository.getSearchedList()

}