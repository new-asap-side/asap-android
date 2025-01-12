package com.asap.aljyo.ui.composable.main.home

data class PrivateGroupState(
    var showPasswordSheet: Boolean = false,
    var joinSuccess: Boolean? = null,
    var isJoinedGroup: Boolean? = null
)
