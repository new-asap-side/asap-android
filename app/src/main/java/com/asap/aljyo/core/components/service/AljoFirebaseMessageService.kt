package com.asap.aljyo.core.components.service

import android.util.Log
import com.asap.aljyo.core.notification.NotificationHandler
import com.asap.data.remote.firebase.FCMTokenManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AljoFirebaseMessageService : FirebaseMessagingService() {
    @Inject
    lateinit var notificationHandler: NotificationHandler

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("MyFirebaseMessageService", "token: $token")
        FCMTokenManager.token = token
    }

    override fun onMessageReceived(message: RemoteMessage) {
        try {
            if (message.notification == null) {
                notificationHandler.showNotification(message.data)
            }
        } catch (e: Exception) {
            Log.e("FirebaseMessageService", "$e")
        }
    }
}