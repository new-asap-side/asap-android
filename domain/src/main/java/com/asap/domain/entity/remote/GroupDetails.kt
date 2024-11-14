package com.asap.domain.entity.remote


sealed class UserGroupType {
    data object Leader: UserGroupType()
    data object Participant: UserGroupType()
    data object NonParticipant: UserGroupType()
}

data class GroupDetails(
    val userType: String
) {
    val userGroupType: UserGroupType get() = when(userType) {
        "leader" -> UserGroupType.Leader
        "participant" -> UserGroupType.Participant
        else -> UserGroupType.NonParticipant
    }
}
