package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class GroupRanking(
    @Json(name = "thumbnail")
    val thumbnail: String = "",
    @Json(name = "user_id")
    val userId: String = "",
    @Json(name = "nickname")
    val nickname: String = "NiCKNAME",
    @Json(name = "score")
    val score: Int = 0
) {

    companion object {
        fun dummy(): GroupRanking = GroupRanking()
    }
}
