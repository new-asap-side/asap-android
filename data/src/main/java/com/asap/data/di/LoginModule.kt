package com.asap.data.di

import com.asap.data.repository.LoginRepository
import com.asap.data.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {
    @Binds
    abstract fun bindLoginService(
        loginServiceImpl: LoginRepositoryImpl
    ): LoginRepository
}