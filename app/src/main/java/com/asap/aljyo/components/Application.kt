package com.asap.aljyo.components

import android.app.Application
import com.asap.aljyo.components.usersetting.UriUtil
import com.asap.aljyo.BuildConfig
import com.asap.aljyo.core.notification.createNotificationChannel
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AljyoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // initial kakao SDK
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        UriUtil.setContext(this)

        // create notification channel
        createNotificationChannel(this)
    }
}