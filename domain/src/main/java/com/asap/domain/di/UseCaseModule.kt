package com.asap.domain.di

import com.asap.domain.usecase.ResultCardUseCase
import com.asap.domain.usecase.ResultCardUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {
    @Binds
    fun bindResultCardUseCase(
        resultCardUseCaseImpl: ResultCardUseCaseImpl
    ): ResultCardUseCase
}