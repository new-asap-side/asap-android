package com.asap.data.di

import com.asap.data.repository.UserRepositoryImpl
import com.asap.domain.repository.UserInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindUserRepository(
        userInfoRepositoryImpl: UserRepositoryImpl
    ) : UserInfoRepository
}