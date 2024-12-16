package com.asap.aljyo.components.group_form

import android.net.Uri
import com.kakao.sdk.common.model.Description
import java.time.LocalDate
import java.time.LocalDateTime

data class GroupScreenState(
    val isPublic: Boolean = false,
    val groupPassword: String? = null,
    val groupImage: Uri? = null,
    val title: String = "",
    val description: String = "",
    val maxPerson: Int = 1,
    val alarmDays: List<String> = emptyList(),
    val alarmTime: String = "",
    val alarmEndDate: LocalDate = LocalDate.now()
)
