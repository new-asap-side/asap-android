package com.asap.aljyo.core.components.edit

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GroupEditState(
    val alarmUnlockContents: String = "",
    val groupImage: Uri? = null,
    val title: String = "",
    val description: String = "",
    val maxPerson: Int = 1,
    val isPublic: Boolean? = null,
    val groupPassword: String? = null,
):Parcelable

