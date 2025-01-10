package com.asap.aljyo.core.components

import android.app.Application
import com.asap.aljyo.BuildConfig
import com.asap.aljyo.core.notification.createNotificationChannel
import com.asap.aljyo.util.PictureUtil
import com.asap.domain.usecase.auth.RegisterTokenUseCase
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class AljyoApplication : Application() {
    @Inject
    lateinit var registerTokenUseCase: RegisterTokenUseCase

    override fun onCreate() {
        super.onCreate()

        // initial kakao SDK
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        PictureUtil.setContext(this)

        createNotificationChannel(this)

        CoroutineScope(Dispatchers.Default).launch {
            registerTokenUseCase()
        }
    }
}