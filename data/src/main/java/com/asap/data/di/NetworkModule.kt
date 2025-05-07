package com.asap.data.di

import com.asap.data.BuildConfig
import com.asap.data.remote.HeaderInterceptor
import com.asap.data.remote.TokenAuthenticator
import com.asap.data.remote.service.AlarmService
import com.asap.data.remote.service.AuthService
import com.asap.data.remote.service.GroupService
import com.asap.data.remote.service.UserService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

internal const val prodBaseUrl = "http://aljo.shop/"
internal const val testBaseUrl = "http://158.179.163.4:8080/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @GenenralOkHttpClient
    @Singleton
    @Provides
    fun provideOkHttpClient(headerInterceptor: HeaderInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(headerInterceptor)

        return client.build()
    }

    @AuthOkHttpClient
    @Singleton
    @Provides
    fun provideAuthOkHttpClient(
        headerInterceptor: HeaderInterceptor,
        authenticator: TokenAuthenticator
    ): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(headerInterceptor)
            .authenticator(authenticator)

        return client.build()
    }

    @AuthRetrofit
    @Singleton
    @Provides
    fun provideAuthRetrofit(
        moshi: Moshi,
        @AuthOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        val baseUrl = if (BuildConfig.DEBUG) testBaseUrl else prodBaseUrl
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .baseUrl(baseUrl)
            .build()
    }

    @GeneralRetrofit
    @Singleton
    @Provides
    fun provideRetrofit(
        moshi: Moshi,
        @GenenralOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        val baseUrl = if (BuildConfig.DEBUG) testBaseUrl else prodBaseUrl
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthService(@GeneralRetrofit retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserService(@AuthRetrofit retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideGroupService(@AuthRetrofit retrofit: Retrofit): GroupService {
        return retrofit.create(GroupService::class.java)
    }

    @Singleton
    @Provides
    fun provideAlarmService(@AuthRetrofit retrofit: Retrofit): AlarmService {
        return retrofit.create(AlarmService::class.java)
    }

}