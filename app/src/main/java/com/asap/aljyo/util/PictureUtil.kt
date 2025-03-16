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

    fun encodeType(uri: Uri?): String? {
        return uri?.let {
            when (it.scheme) {
                ContentResolver.SCHEME_ANDROID_RESOURCE -> {
                    encodeDrawableToBase64(it)
                }

                ContentResolver.SCHEME_CONTENT -> {
                    encodeUriToBase64(it)
                }

                else -> uri.toString()
            }
        }
    }


    private fun encodeUriToBase64(uri: Uri): String? {
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

    private fun encodeDrawableToBase64(uri: Uri): String? {
        return try {
            val resourceId = uri.lastPathSegment?.toInt()
            if (resourceId != null) {
                val drawable = applicationContext.resources.openRawResource(resourceId)
                val byteArray = drawable.use { it.readBytes() }
                Base64.encodeToString(byteArray, Base64.DEFAULT)
            } else null
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

    fun getProfileItemById(profileItemId: Int?): Int? {
        return when(profileItemId) {
            121 -> R.drawable.ic_custom_1
            122 -> R.drawable.ic_custom_2
            123 -> R.drawable.ic_custom_3
            124 -> R.drawable.ic_custom_4
            125 -> R.drawable.ic_custom_5
            126 -> R.drawable.ic_custom_6
            else -> null
        }
    }

    fun getProfileItemByName(profileItemName: String?): Int? {
        return when(profileItemName) {
            "20_000" -> R.drawable.ic_custom_1
            "50_000" -> R.drawable.ic_custom_2
            "100_000" -> R.drawable.ic_custom_3
            "200_000" -> R.drawable.ic_custom_4
            "400_000" -> R.drawable.ic_custom_5
            "700_000" -> R.drawable.ic_custom_6
            else -> null
        }
    }
}
