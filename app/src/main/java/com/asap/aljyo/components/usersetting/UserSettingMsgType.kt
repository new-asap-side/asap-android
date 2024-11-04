package com.asap.aljyo.components.usersetting

enum class UserSettingMsgType(val msg: String) {
    LengthError("닉네임은 2~8자 사이여야 합니다."),
    DuplicateNicknameError("중복된 닉네임입니다."),
    Success("사용가능한 닉네임입니다."),
    None("")
}