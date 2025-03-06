package com.asap.domain.di

import com.asap.domain.usecase.group.FetchGroupUseCase
import com.asap.domain.usecase.group.SearchGroupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseProvider {
    @Provides
    fun provideFetchGroupUseCase(
        searchGroupUseCase: SearchGroupUseCase
    ): FetchGroupUseCase = FetchGroupUseCase(
        searchGroupUseCase = searchGroupUseCase
    )
}