package com.asap.utility.datetime

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DateTimeModule {
    @CurrentTimeDot
    @Provides
    fun provideCurrentTimeDotParser(): DateTimeParser = CurrentTimeDotParser()
}