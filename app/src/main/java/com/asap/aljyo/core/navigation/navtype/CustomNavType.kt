package com.asap.aljyo.core.navigation.navtype

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.asap.aljyo.core.components.edit.GroupEditState
import com.asap.domain.entity.remote.Alarm
import com.google.gson.JsonParseException
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object CustomNavType {
    val groupEditType = object : NavType<GroupEditState>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): GroupEditState? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, GroupEditState::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelable(key)
            }
        }

        override fun parseValue(value: String): GroupEditState {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val adapter = moshi.adapter(GroupEditState::class.java)
            val decodedValue = Uri.decode(value)
            return adapter.lenient().fromJson(decodedValue) ?: throw IllegalArgumentException("Invaild Json")
        }

        override fun put(bundle: Bundle, key: String, value: GroupEditState) {
            bundle.putParcelable(key, value)
        }

    }
}