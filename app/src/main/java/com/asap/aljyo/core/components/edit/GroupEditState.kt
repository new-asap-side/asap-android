package com.asap.aljyo.core.components.edit

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GroupEditState(
    val groupId: Int = 0,
    val alarmUnlockContents: String = "",
    val groupImage: String? = null,
    val title: String = "",
    val description: String = "",
    val currentPerson: Int = 0,
    val isPublic: Boolean = true,
    val groupPassword: String? = null,
):Parcelable {
    val buttonState: Boolean
        get() = title.isNotBlank() &&
                description.isNotBlank()
}