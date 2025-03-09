package com.asap.domain.usecase.group

import com.asap.domain.entity.local.SearchEntity
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class SearchGroupUseCaseWrapper(
    val searchGroupUseCase: SearchGroupUseCase,
    val getSearchedListUseCase: GetSearchedListUseCase,
    val deleteSearchEntityUseCase: DeleteSearchEntityUseCase
)

// 알람 그룹 검색
interface SearchGroupUseCase {
    suspend operator fun invoke(query: String): Flow<List<AlarmGroup>>
}

// 알람 그룹 검색 기록 조회
interface GetSearchedListUseCase {
    suspend operator fun invoke(): List<SearchEntity>
}

// 알람 그룹 검색 기록 단일 삭제
interface DeleteSearchEntityUseCase {
    suspend operator fun invoke(entity: SearchEntity)
}

class SearchGroupUseCaseImpl @Inject constructor(
    private val repository: GroupRepository
) : SearchGroupUseCase {
    override suspend fun invoke(query: String) = repository.searchGroup(query)
}

class GetSearchedListUseCaseImpl @Inject constructor(
    private val repository: GroupRepository
) : GetSearchedListUseCase {
    override suspend fun invoke(): List<SearchEntity> = repository.getSearchedList()
}

class DeleteSearchEntityUseCaseImpl @Inject constructor(
    private val repository: GroupRepository
) : DeleteSearchEntityUseCase {
    override suspend fun invoke(entity: SearchEntity) {
        repository.deleteSearchEntity(entity)
    }
}