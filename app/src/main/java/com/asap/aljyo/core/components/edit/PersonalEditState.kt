package com.asap.aljyo.core.components.edit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonalEditState (
    val alarmType: String = "",
    val musicTitle: String? = null,
    val alarmVolume: Float? = null
): Parcelable {

}