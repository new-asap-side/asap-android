package com.asap.data.remote.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostReportGroupRequest(
    @Json(name = "groupId")
    val groupId: Int,
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "reportDetailText")
    val survey: String
)


