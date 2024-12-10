package com.asap.aljyo.core.components.usersetting

enum class UserSettingMsgType(val msg: String) {
    LengthError("닉네임은 2~8자 사이여야 합니다."),
    DuplicateNicknameError("중복된 닉네임입니다."),
    FormatError("한글 자음이나 모음 하나만 단독으로 입력할 수 없습니다."),
    Success("사용가능한 닉네임입니다."),
    None("")
}