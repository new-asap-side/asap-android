package com.asap.aljyo.core.components

import android.app.Application
import android.util.Log
import com.asap.aljyo.BuildConfig
import com.asap.aljyo.util.PictureUtil
import com.asap.data.remote.firebase.FCMTokenManager
import com.asap.data.repository.AuthRepositoryImpl.Companion.TAG
import com.asap.domain.repository.AuthRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class AljyoApplication : Application() {
    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate() {
        super.onCreate()

        // initial kakao SDK
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        PictureUtil.setContext(this)

        CoroutineScope(Dispatchers.Default).launch {
            authRepository.registerToken()
        }
    }
}