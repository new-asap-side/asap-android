package com.asap.aljyo.components.usersetting

import android.net.Uri

data class UserSettingState (
    val selectedProfileImage: Uri? = null,
    val nickname: String = "",
    val errorMsg: UserSettingErrorType = UserSettingErrorType.None
)
