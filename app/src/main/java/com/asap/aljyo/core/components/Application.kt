package com.asap.aljyo.core.components

import android.app.Application
import com.asap.aljyo.BuildConfig
import com.asap.aljyo.util.PictureUtil
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AljyoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // initial kakao SDK
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        PictureUtil.setContext(this)
    }
}