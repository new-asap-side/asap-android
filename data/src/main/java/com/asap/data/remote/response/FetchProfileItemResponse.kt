package com.asap.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FetchProfileItemResponse(
    @Json(name = "profileItems")
    val profileItems: List<ProfileItem>,
    @Json(name = "totalRankScore")
    val totalRankScore: Int
)

@JsonClass(generateAdapter = true)
data class ProfileItem(
    @Json(name = "is_red_point")
    val isRedPoint: Boolean,
    @Json(name = "is_unlocked")
    val isUnlocked: Boolean,
    @Json(name = "is_used")
    val isUsed: Boolean,
    @Json(name = "item_name")
    val itemName: String,
    @Json(name = "profile_id")
    val profileId: Int
)