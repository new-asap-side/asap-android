package com.asap.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostGroupCreateResponse(
    @Json(name = "groupId")
    val groupId: Int,
    @Json(name = "message")
    val message: String
)

