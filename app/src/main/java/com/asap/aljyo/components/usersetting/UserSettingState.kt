package com.asap.aljyo.components.usersetting

import android.net.Uri

data class UserSettingState (
    val selectedProfileImage: Uri? = null,
    val nickname: String = "",
    val msg: UserSettingMsgType = UserSettingMsgType.None
)
