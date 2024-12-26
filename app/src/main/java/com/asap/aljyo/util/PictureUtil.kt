package com.asap.aljyo.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Base64
import com.asap.aljyo.BuildConfig
import com.asap.aljyo.R

object PictureUtil {
    private lateinit var applicationContext: Context

    fun setContext(context: Context) {
        applicationContext = context.applicationContext
    }


    fun getStringFromUri(uri: Uri?): String? {
        return try {
            uri?.let {
                applicationContext.contentResolver.openInputStream(it)?.use { inputStream ->
                    val byteArray = inputStream.readBytes()
                    Base64.encodeToString(byteArray, Base64.DEFAULT)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    val groupRandomImage = listOf(
        Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${BuildConfig.APPLICATION_ID}/${R.drawable.group_random_1}"),
        Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${BuildConfig.APPLICATION_ID}/${R.drawable.group_random_2}"),
        Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${BuildConfig.APPLICATION_ID}/${R.drawable.group_random_3}"),
        Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${BuildConfig.APPLICATION_ID}/${R.drawable.group_random_4}")
    )
}
