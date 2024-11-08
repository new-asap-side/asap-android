package com.asap.aljyo.components

import android.app.Application
import androidx.compose.ui.res.stringResource
import com.asap.aljyo.R
import com.asap.aljyo.components.usersetting.UriUtil
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AljyoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // initial kakao SDK
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
        UriUtil.setContext(this)
    }
}