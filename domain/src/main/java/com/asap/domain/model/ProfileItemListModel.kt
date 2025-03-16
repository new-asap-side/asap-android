package com.asap.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ProfileItemListModel(
    val totalRankScore: Int,
    val profileItems: List<ProfileItemModel>
) {
    data class ProfileItemModel(
        val isRedPoint: Boolean,
        val isUnlocked: Boolean,
        val isUsed: Boolean,
        val itemName: String,
        val profileId: Int
    )
}
