package com.asap.data.di

import com.asap.data.local.source.TokenDataSource
import com.asap.data.local.source.TokenDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalModule {
    @Singleton
    @Binds
    fun bindTokenDataSource(tokenDatasource: TokenDataSourceImpl) : TokenDataSource
}