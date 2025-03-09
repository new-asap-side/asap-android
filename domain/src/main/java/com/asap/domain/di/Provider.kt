package com.asap.domain.di

import com.asap.domain.usecase.group.DeleteSearchEntityUseCase
import com.asap.domain.usecase.group.GetSearchedListUseCase
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
        deleteSearchEntityUseCase: DeleteSearchEntityUseCase
    ): SearchGroupUseCaseWrapper = SearchGroupUseCaseWrapper(
        searchGroupUseCase = searchGroupUseCase,
        getSearchedListUseCase = getSearchedListUseCase,
        deleteSearchEntityUseCase = deleteSearchEntityUseCase
    )
}