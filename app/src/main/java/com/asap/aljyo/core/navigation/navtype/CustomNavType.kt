package com.asap.aljyo.core.navigation.navtype

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.asap.aljyo.core.components.edit.GroupEditState
import com.asap.aljyo.core.components.edit.PersonalEditState
import com.asap.domain.entity.remote.Alarm
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay

object CustomNavType {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val groupEditType = object : NavType<GroupEditState>(
        isNullableAllowed = false
    ) {
        val adapter = moshi.adapter(GroupEditState::class.java)

        override fun serializeAsValue(value: GroupEditState): String {
            return Uri.encode(adapter.toJson(value))
        }

        override fun parseValue(value: String): GroupEditState {
            return adapter.lenient().fromJson(Uri.decode(value)) ?: throw IllegalArgumentException("Invalid Format")
        }

        override fun put(bundle: Bundle, key: String, value: GroupEditState) {
            bundle.putParcelable(key, value)
        }

        override fun get(bundle: Bundle, key: String): GroupEditState? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, GroupEditState::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelable(key)
            }
        }
    }

    val PersonalEditType = object : NavType<PersonalEditState>(
        isNullableAllowed = false
    ) {
        val adapter = moshi.adapter(PersonalEditState::class.java)

        override fun serializeAsValue(value: PersonalEditState): String {
            return Uri.encode(adapter.toJson(value))
        }

        override fun parseValue(value: String): PersonalEditState {
            return adapter.lenient().fromJson(Uri.decode(value)) ?: throw IllegalArgumentException("Invalid Format")
        }

        override fun put(bundle: Bundle, key: String, value: PersonalEditState) {
            bundle.putParcelable(key, value)
        }

        override fun get(bundle: Bundle, key: String): PersonalEditState? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, PersonalEditState::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelable(key)
            }
        }
    }
}