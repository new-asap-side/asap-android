package com.asap.utility.di

import com.asap.utility.datetime.BaseDateTimeCalculator
import com.asap.utility.datetime.DateTimeCalculator
import com.asap.utility.datetime.DateTimeParser
import com.asap.utility.datetime.Seconds
import com.asap.utility.datetime.SecondsCalculator
import com.asap.utility.datetime.TimeColon
import com.asap.utility.datetime.TimeColonParser
import com.asap.utility.datetime.TimeDot
import com.asap.utility.datetime.TimeDotParser
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
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

@Module
@InstallIn(SingletonComponent::class)
object BaseDateTimeCalculatorModule {
    @Seconds
    @Provides
    fun provdeSecondCalculator(): DateTimeCalculator = SecondsCalculator()
}

//@Module
//@InstallIn(ViewModelComponent::class)
//interface DateTimeCalcultorInterfaceModule {
//    @Seconds
//    @Binds
//    fun bindSecondsCalculator(@Seconds calculator: BaseDateTimeCalculator): DateTimeCalculator
//}
