package com.asap.aljyo.core.navigation.navtype

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.asap.domain.entity.remote.alarm.AlarmPayload
import com.google.gson.JsonParseException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object AlarmNavType : NavType<AlarmPayload>(false) {

    override val name: String
        get() = "alarm"

    override fun get(bundle: Bundle, key: String): AlarmPayload? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, AlarmPayload::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): AlarmPayload {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return moshi.adapter(AlarmPayload::class.java).lenient().fromJson(value)
            ?: throw JsonParseException("Invalid json")
    }

    override fun put(bundle: Bundle, key: String, value: AlarmPayload) {
        bundle.putParcelable(key, value)
    }

}