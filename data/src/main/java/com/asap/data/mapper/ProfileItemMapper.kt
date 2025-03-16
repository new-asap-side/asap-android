package com.asap.data.mapper

import com.asap.data.remote.response.FetchProfileItemResponse
import com.asap.data.remote.response.ProfileItemResponse
import com.asap.domain.model.ProfileItemListModel

object ProfileItemListMapper {
    fun toDomain(response: FetchProfileItemResponse): ProfileItemListModel {
        return with(response) {
            ProfileItemListModel(
                totalRankScore = response.totalRankScore,
                profileItems = response.profileItems.map { ProfileItemMapper.toDomain(it) }
            )
        }
    }

    object ProfileItemMapper {
        fun toDomain(response: ProfileItemResponse): ProfileItemListModel.ProfileItemModel {
            return with(response) {
                ProfileItemListModel.ProfileItemModel(
                    isRedPoint = isRedPoint,
                    isUnlocked = isUnlocked,
                    isUsed = isUsed,
                    itemName = itemName,
                    profileId = profileId
                )
            }
        }
    }
}