package com.asap.data.remote.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessageService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("MyFirebaseMessageService", "token: $token")
    }
}