package com.asap.data.di

import com.asap.data.repository.AuthRepositoryImpl
import com.asap.data.repository.GroupRepositoryImpl
import com.asap.data.repository.UserRepositoryImpl
import com.asap.domain.repository.AuthRepository
import com.asap.domain.repository.GroupRepository
import com.asap.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    fun bindAlarmGroupRepository(
        groupRepositoryImpl: GroupRepositoryImpl
    ): GroupRepository

    @Binds
    fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}

