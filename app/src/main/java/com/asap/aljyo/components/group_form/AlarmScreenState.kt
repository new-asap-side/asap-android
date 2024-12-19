package com.asap.aljyo.components.group_form

data class AlarmScreenState(
    val alarmUnlockContents: String = "",
    val alarmType: String = "",
    val musicTitle: String = "노래를 선택해주세요!",
    val alarmVolume: Float = 10f
) {
    val buttonState: Boolean
        get() = when (alarmType) {
            "SOUND", "ALL" -> musicTitle.isNotBlank() && alarmVolume > 1
            "VIBRATION" -> true
            else -> false
        }
}

