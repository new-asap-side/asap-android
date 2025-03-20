package com.asap.aljyo.core.components.usersetting

import android.net.Uri

data class UserSettingState (
    val selectedProfileImage: Uri? = null,
    val profileItem: Int? = null,
    val nickname: String = "",
    val msg: UserSettingMsgType = UserSettingMsgType.None
)
