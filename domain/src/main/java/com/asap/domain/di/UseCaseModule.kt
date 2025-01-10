package com.asap.domain.di

import com.asap.domain.usecase.alarm.ActivateAlarmUseCase
import com.asap.domain.usecase.alarm.ActivateAlarmUseCaseImpl
import com.asap.domain.usecase.alarm.AlarmOffUseCase
import com.asap.domain.usecase.alarm.AlarmOffUseCaseImpl
import com.asap.domain.usecase.alarm.DeactivateAlarmUseCase
import com.asap.domain.usecase.alarm.DeactivateAlarmUseCaseImpl
import com.asap.domain.usecase.alarm.FetchAlarmOffRateUseCase
import com.asap.domain.usecase.alarm.FetchAlarmOffRateUseCaseImpl
import com.asap.domain.usecase.alarm.GetDeactivatedAlarmListUseCase
import com.asap.domain.usecase.alarm.GetDeactivatedAlarmlistUseCaseImpl
import com.asap.domain.usecase.auth.AuthKakaoUseCase
import com.asap.domain.usecase.auth.AuthKakaoUseCaseImpl
import com.asap.domain.usecase.auth.CacheAuthKakaoUseCaseImpl
import com.asap.domain.usecase.auth.CacheAuthUseCase
import com.asap.domain.usecase.auth.CheckAuthUseCaseImpl
import com.asap.domain.usecase.auth.CheckCachedAuthUseCase
import com.asap.domain.usecase.auth.RefreshTokenUseCase
import com.asap.domain.usecase.auth.RefreshTokenUseCaseImpl
import com.asap.domain.usecase.auth.RegisterTokenUseCase
import com.asap.domain.usecase.auth.RegisterTokenUseCaseImpl
import com.asap.domain.usecase.group.FetchAlarmListUseCase
import com.asap.domain.usecase.group.FetchAlarmListUseCaseImpl
import com.asap.domain.usecase.group.FetchGroupDetailsUseCase
import com.asap.domain.usecase.group.FetchGroupDetailsUseCaseImpl
import com.asap.domain.usecase.group.FetchGroupRankingUseCase
import com.asap.domain.usecase.group.FetchGroupRankingUseCaseImpl
import com.asap.domain.usecase.group.FetchLatestGroupUseCase
import com.asap.domain.usecase.group.FetchLatestGroupUseCaseImpl
import com.asap.domain.usecase.group.FetchPopularGroupUseCase
import com.asap.domain.usecase.group.FetchPopularGroupUseCaseImpl
import com.asap.domain.usecase.group.FetchRankingNumberUseCase
import com.asap.domain.usecase.group.FetchRankingNumberUseCaseImpl
import com.asap.domain.usecase.group.JoinGroupUseCase
import com.asap.domain.usecase.group.JoinGroupUseCaseImpl
import com.asap.domain.usecase.user.CheckCachedProfileUseCase
import com.asap.domain.usecase.user.CheckCachedProfileUseCaseImpl
import com.asap.domain.usecase.user.DeleteLocalUserInfoUseCase
import com.asap.domain.usecase.user.DeleteLocalUserInfoUseCaseImpl
import com.asap.domain.usecase.user.DeleteUserInfoUseCase
import com.asap.domain.usecase.user.DeleteUserInfoUseCaseImpl
import com.asap.domain.usecase.user.FetchUserProfileUseCase
import com.asap.domain.usecase.user.FetchUserProfileUseCaseImpl
import com.asap.domain.usecase.user.GetUserInfoUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {
    /**
     * Auth usecase
     */
    @Binds
    fun bindRegisterTokenUseCase(useCaseImpl: RegisterTokenUseCaseImpl): RegisterTokenUseCase

    @Binds
    fun bindRefreshTokenUseCase(useCaseImpl: RefreshTokenUseCaseImpl): RefreshTokenUseCase

    @Binds
    fun bindAuthKakaoUseCase(usecaseImpl: AuthKakaoUseCaseImpl): AuthKakaoUseCase

    @Binds
    fun bindCacheAuthUseCase(usecaseImpl: CacheAuthKakaoUseCaseImpl): CacheAuthUseCase

    @Binds
    fun bindCheckCachedAuthUseCase(usecaseImpl: CheckAuthUseCaseImpl): CheckCachedAuthUseCase

    /**
     * User usecase
     */
    @Binds
    fun bindCheckCacheUseCase(
        usecaseImpl: CheckCachedProfileUseCaseImpl
    ): CheckCachedProfileUseCase

    @Binds
    fun bindGetUserInfoUseCase(
        getUserInfoUseCaseImpl: GetUserInfoUseCaseImpl
    ): GetUserInfoUseCase

    @Binds
    fun bindFetchUserProfileUseCase(
        fetchUserProfileUseCaseImpl: FetchUserProfileUseCaseImpl
    ): FetchUserProfileUseCase

    @Binds
    fun bindPostJoinGroupUseCase(
        joinGroupUseCaseImpl: JoinGroupUseCaseImpl
    ): JoinGroupUseCase

    @Binds
    fun bindDeleteUseCase(
        deleteUserInfoUseCaseImpl: DeleteUserInfoUseCaseImpl
    ): DeleteUserInfoUseCase

    @Binds
    fun bindDeleteLocalUseCase(
        deleteLocalUserInfoUseCaseImpl: DeleteLocalUserInfoUseCaseImpl
    ): DeleteLocalUserInfoUseCase

    /**
     * Group usecase
     * **/
    @Binds
    fun bindFetchPopularGroupUseCae(
        fetchPopularGroupUseCaseImpl: FetchPopularGroupUseCaseImpl
    ): FetchPopularGroupUseCase

    @Binds
    fun bindFetchLatestGroupUseCase(
        fetchGroupRankingUseCaseImpl: FetchLatestGroupUseCaseImpl
    ): FetchLatestGroupUseCase

    @Binds
    fun bindFetchGroupRankingUseCase(
        fetchGroupRankingUseCaseImpl: FetchGroupRankingUseCaseImpl
    ): FetchGroupRankingUseCase

    @Binds
    fun bindFetchRankingNumberUseCase(
        fetchRankingNumberUseCaseImpl: FetchRankingNumberUseCaseImpl
    ): FetchRankingNumberUseCase

    @Binds
    fun bindFetchAlarmListUseCase(
        fetchAlarmListUseCaseImpl: FetchAlarmListUseCaseImpl
    ): FetchAlarmListUseCase

    @Binds
    fun bindFetchGroupDetailsUseCase(
        fetchGroupDetailsUseCaseImpl: FetchGroupDetailsUseCaseImpl
    ): FetchGroupDetailsUseCase

    /**
     * Alarm usecases
     */
    @Binds
    fun bindGetDeactivatedAlarmListUseCase(
        getDeactivatedAlarmlistUseCaseImpl: GetDeactivatedAlarmlistUseCaseImpl
    ): GetDeactivatedAlarmListUseCase

    @Binds
    fun bindActivateAlarmUseCase(
        activateAlarmUseCaseImpl: ActivateAlarmUseCaseImpl
    ): ActivateAlarmUseCase

    @Binds
    fun bindDeactivateAlarmUseCase(
        deactivateAlarmUseCaseImpl: DeactivateAlarmUseCaseImpl
    ): DeactivateAlarmUseCase

    @Binds
    fun bindAlarmOffUseCase(
        alarmOffUseCaseImpl: AlarmOffUseCaseImpl
    ): AlarmOffUseCase

    @Binds
    fun bindFetchAlarmOffUseCase(
        fetchAlarmOffRateUseCaseImpl: FetchAlarmOffRateUseCaseImpl
    ): FetchAlarmOffRateUseCase
}