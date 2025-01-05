package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// admin/user body
@JsonClass(generateAdapter = true)
data class DeleteUserRequestBody(
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "survey")
    val survey: String
)