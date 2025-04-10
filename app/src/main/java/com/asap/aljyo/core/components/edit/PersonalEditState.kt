package com.asap.aljyo.core.components.edit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonalEditState (
    val nickName: String = "",
    val isEditMode: Boolean = false,
    val alarmType: String = "",
    val musicTitle: String? = null,
    val alarmVolume: Float = 10f
): Parcelable {
    val buttonState: Boolean
        get() = when (alarmType) {
            "SOUND", "ALL" -> musicTitle.isNullOrEmpty().not()
            "VIBRATION" -> true
            else -> false
        }
}