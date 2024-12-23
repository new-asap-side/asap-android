package com.asap.aljyo.components.group_form

import android.net.Uri
import com.asap.aljyo.util.format
import com.kakao.sdk.common.model.Description
import java.time.LocalDate
import java.time.LocalDateTime

data class GroupScreenState(
    val isPublic: Boolean? = null,
    val groupPassword: String? = null,
    val groupImage: Uri? = null,
    val title: String = "",
    val description: String = "",
    val maxPerson: Int = 1,
    val alarmDays: List<String> = emptyList(),
    val alarmTime: String = LocalDateTime.now().format(),
    val alarmEndDate: LocalDate? = null
) {
    val typeButtonState: Boolean
        get() = when (isPublic) {
            true -> true
            false -> groupPassword?.length == 4
            null -> false
        }

    val buttonState: Boolean
        get() = title.isNotBlank() &&
                description.isNotBlank() &&
                alarmDays.isNotEmpty() &&
                alarmTime.isNotBlank() &&
                alarmEndDate != null
}
