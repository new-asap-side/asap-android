package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GroupJoinResponse(
    @Json(name = "groupId")
    val groupId: Int,
    @Json(name = "message")
    val message: String
)