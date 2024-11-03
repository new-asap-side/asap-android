package com.asap.aljyo.components.usersetting

enum class UserSettingErrorType(val msg: String) {
    LengthError("닉네임은 2~8자 사이여야 합니다."),
    DuplicateNicknameError("닉네임에 잘못된 문자가 포함되어 있습니다."),
    None("")
}