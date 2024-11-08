package com.asap.aljyo.components.usersetting

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import android.util.Base64

object UriUtil {
    private lateinit var applicationContext: Context

    fun setContext(context: Context) {
        applicationContext = context.applicationContext
    }

    fun getStringFromUri(uri: Uri): String? {
        return try {
            val inputStream = applicationContext.contentResolver.openInputStream(uri)
            val byteArray = inputStream?.readBytes()
            inputStream?.close()
            byteArray?.let { Base64.encodeToString(it, Base64.DEFAULT) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}