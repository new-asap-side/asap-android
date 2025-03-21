package com.asap.domain.di

import com.asap.domain.usecase.group.DeleteAllSearchEntityUseCase
import com.asap.domain.usecase.group.DeleteSearchEntityUseCase
import com.asap.domain.usecase.group.FetchGroupRankingUseCase
import com.asap.domain.usecase.group.FetchRankingNumberUseCase
import com.asap.domain.usecase.group.FetchTodayRankingUseCase
import com.asap.domain.usecase.group.GetSearchedListUseCase
import com.asap.domain.usecase.group.GroupRankingUseCase
import com.asap.domain.usecase.group.SearchGroupUseCase
import com.asap.domain.usecase.group.SearchGroupUseCaseWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseProvider {
    @Provides
    fun provideFetchGroupUseCase(
        searchGroupUseCase: SearchGroupUseCase,
        getSearchedListUseCase: GetSearchedListUseCase,
        deleteSearchEntityUseCase: DeleteSearchEntityUseCase,
        deleteAllSearchEntityUseCase: DeleteAllSearchEntityUseCase
    ): SearchGroupUseCaseWrapper = SearchGroupUseCaseWrapper(
        searchGroupUseCase = searchGroupUseCase,
        getSearchedListUseCase = getSearchedListUseCase,
        deleteSearchEntityUseCase = deleteSearchEntityUseCase,
        deleteAllSearchEntityUseCase = deleteAllSearchEntityUseCase
    )

    @Provides
    fun provideGroupRankingUseCase(
        fetchGroupRankingUseCase: FetchGroupRankingUseCase,
        fetchTodayRankingUseCase: FetchTodayRankingUseCase
    ): GroupRankingUseCase = GroupRankingUseCase(
        fetchGroupRankingUseCase = fetchGroupRankingUseCase,
        fetchTodayRankingUseCase = fetchTodayRankingUseCase
    )
}