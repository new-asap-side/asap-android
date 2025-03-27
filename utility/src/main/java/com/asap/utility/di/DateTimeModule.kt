package com.asap.utility.di

import com.asap.utility.datetime.DateTimeParser
import com.asap.utility.datetime.TimeColon
import com.asap.utility.datetime.TimeColonParser
import com.asap.utility.datetime.TimeDot
import com.asap.utility.datetime.TimeDotParser
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