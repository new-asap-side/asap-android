package com.asap.aljyo.di

import com.asap.aljyo.core.components.group_details.GroupDetailsViewModel
import com.asap.aljyo.core.components.viewmodel.GroupRankingViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun groupDetailsViewModelFactory(): GroupDetailsViewModel.GroupDetailsViewModelFactory

    fun groupRankingViewModelFactory(): GroupRankingViewModel.GroupRankingViewModelFactory
}