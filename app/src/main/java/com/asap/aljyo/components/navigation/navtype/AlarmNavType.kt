package com.asap.aljyo.components.navigation.navtype

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.asap.domain.entity.remote.Alarm
import com.google.gson.JsonParseException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object AlarmNavType : NavType<Alarm>(false) {

    override val name: String
        get() = "alarm"

    override fun get(bundle: Bundle, key: String): Alarm? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, Alarm::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): Alarm {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return moshi.adapter(Alarm::class.java).fromJson(value)
            ?: throw JsonParseException("Invalid json")
    }

    override fun put(bundle: Bundle, key: String, value: Alarm) {
        bundle.putParcelable(key, value)
    }

}