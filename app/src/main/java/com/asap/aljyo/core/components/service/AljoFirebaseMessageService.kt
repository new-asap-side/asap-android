package com.asap.aljyo.core.components.service

import android.util.Log
import com.asap.data.remote.firebase.FCMTokenManager
import com.google.firebase.messaging.FirebaseMessagingService

class AljoFirebaseMessageService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("MyFirebaseMessageService", "token: $token")
        FCMTokenManager.token = token
    }
}