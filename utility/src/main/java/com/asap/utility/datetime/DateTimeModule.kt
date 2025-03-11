package com.asap.utility.datetime

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DateTimeModule {
    @TimeDot
    @Provides
    fun provideTimeDotParser(): DateTimeParser = TimeDotParser()

    @TimeColon
    @Provides
    fun provideTimeColonParser(): DateTimeParser = TimeColonParser()
}