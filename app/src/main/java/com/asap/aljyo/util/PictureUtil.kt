package com.asap.aljyo.util

import android.content.Context
import android.net.Uri
import android.util.Base64

object PictureUtil {
    private lateinit var applicationContext: Context

    fun setContext(context: Context) {
        applicationContext = context.applicationContext
    }


    fun getStringFromUri(uri: Uri): String? {
        return try {
            applicationContext.contentResolver.openInputStream(uri)?.use { inputStream ->
                val byteArray = inputStream.readBytes()
                Base64.encodeToString(byteArray, Base64.DEFAULT)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}