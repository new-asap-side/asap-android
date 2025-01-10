package com.asap.aljyo.di

import com.asap.aljyo.core.notification.AlarmMessageHandler
import com.asap.aljyo.core.notification.MessageHandler
import com.asap.data.di.NetworkModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class NotificationHandlerModule {
    @Binds
    abstract fun bindAlarmNotificationHandler(
        alarmMessageHandler: AlarmMessageHandler
    ) : MessageHandler
}