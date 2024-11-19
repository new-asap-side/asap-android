package com.asap.domain.di

import com.asap.domain.usecase.ResultCardUseCase
import com.asap.domain.usecase.ResultCardUseCaseImpl
import com.asap.domain.usecase.group.FetchGroupRankingUseCase
import com.asap.domain.usecase.group.FetchGroupRankingUseCaseImpl
import com.asap.domain.usecase.group.JoinGroupUseCase
import com.asap.domain.usecase.group.JoinGroupUseCaseImpl
import com.asap.domain.usecase.user.AuthKakaoUseCase
import com.asap.domain.usecase.user.AuthKakaoUseCaseImpl
import com.asap.domain.usecase.user.CacheUserUseCase
import com.asap.domain.usecase.user.CacheUserUseCaseImpl
import com.asap.domain.usecase.user.CheckCacheUserCase
import com.asap.domain.usecase.user.CheckCacheUserCaseImpl
import com.asap.domain.usecase.user.FetchFCMTokenUseCase
import com.asap.domain.usecase.user.FetchFCMTokenUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {
    @Binds
    fun bindKakaoLoginUseCase(
        kakaoLoginUseCaseImpl: AuthKakaoUseCaseImpl
    ): AuthKakaoUseCase

    @Binds
    fun bindCacheKakaoUserUseCase(
        cacheUserUseCaseImpl: CacheUserUseCaseImpl
    ): CacheUserUseCase

    @Binds
    fun bindCheckCacheUseCase(
        checkCacheUserCaseImpl: CheckCacheUserCaseImpl
    ): CheckCacheUserCase

    @Binds
    fun bindResultCardUseCase(
        resultCardUseCaseImpl: ResultCardUseCaseImpl
    ): ResultCardUseCase

    @Binds
    fun bindFetchFCMTokenUseCase(
        fetchFCMTokenUseCaseImpl: FetchFCMTokenUseCaseImpl
    ): FetchFCMTokenUseCase

    @Binds
    fun bindPostJoinGroupUseCase(
        joinGroupUseCaseImpl: JoinGroupUseCaseImpl
    ): JoinGroupUseCase

    @Binds
    fun bindFetchGroupRankingUseCase(
        fetchGroupRankingUseCaseImpl: FetchGroupRankingUseCaseImpl
    ): FetchGroupRankingUseCase
}