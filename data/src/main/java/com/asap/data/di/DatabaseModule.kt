package com.asap.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.asap.data.local.AppDatabase
import com.asap.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import androidx.datastore.preferences.core.Preferences
import com.asap.data.BuildConfig
import com.asap.data.local.MIGRATION_1_2
import com.asap.data.local.MIGRATION_2_3
import com.asap.data.local.MIGRATION_3_4

import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "aljo.db")
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build()

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create (
            produceFile = { context.preferencesDataStoreFile(BuildConfig.TOKEN_DATASTORE_NAME) }
        )
    }

    @Provides
    @Singleton
    fun providesSp(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_preferences",Context.MODE_PRIVATE)
    }
}