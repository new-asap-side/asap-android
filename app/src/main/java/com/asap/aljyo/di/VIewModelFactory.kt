package com.asap.aljyo.di

import com.asap.aljyo.components.group_ranking.GroupRankingViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun groupRankingViewModelFactory(): GroupRankingViewModel.GroupRankingViewModelFactory
}