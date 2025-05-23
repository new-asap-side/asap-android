package com.asap.aljyo.core.components.service

import android.util.Log
import com.asap.aljyo.core.notification.MessageHandler
import com.asap.domain.entity.remote.auth.TokenManager

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AljoFirebaseMessageService : FirebaseMessagingService() {
    @Inject
    lateinit var messageHandler: MessageHandler

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "token: $token")
        TokenManager.fcmToken = token
    }

    override fun onMessageReceived(message: RemoteMessage) {
        try {
            Log.d(TAG, "onMessageReceived: $message")
            if (message.notification == null) {
                messageHandler.handleMessage(message.data)
            }
        } catch (e: Exception) {
            Log.e(TAG, "$e")
        }
    }

    companion object {
        const val TAG = "FirebaseMessageService"
    }
}