package com.asap.aljyo.core.components.group_form

data class AlarmScreenState(
    val nickName: String? = "",
    val alarmUnlockContents: String = "",
    val alarmType: String = "",
    val musicTitle: String? = null,
    val alarmVolume: Float = 10f
) {
    val buttonState: Boolean
        get() = when (alarmType) {
            "SOUND", "ALL" -> musicTitle.isNullOrEmpty().not()
            "VIBRATION" -> true
            else -> false
        }
}

