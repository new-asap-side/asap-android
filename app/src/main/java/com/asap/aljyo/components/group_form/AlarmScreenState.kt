package com.asap.aljyo.components.group_form

data class AlarmScreenState(
    val alarmUnlockContents: String = "",
    val alarmType: String = "",
    val musicTitle: String? = null,
    val alarmVolume: Float? = null
) {
    val buttonState: Boolean
        get() = when (alarmType) {
            "SOUND", "ALL" -> musicTitle.isNullOrEmpty().not()
            "VIBRATION" -> true
            else -> false
        }
}

