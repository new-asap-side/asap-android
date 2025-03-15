package com.asap.aljyo.core.components.main

import android.icu.text.DecimalFormat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.R
import com.asap.domain.model.ProfileItemListModel
import com.asap.domain.usecase.user.FetchProfileItemUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomizeProfileViewModel @Inject constructor(
    private val fetchProfileItemUseCase: FetchProfileItemUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
): ViewModel(){
    private val _state = MutableStateFlow(CustomizeProfileScreenState())
    val state = _state.asStateFlow()

    init {
        fetchProfileItem()
    }

    private fun fetchProfileItem() {
        viewModelScope.launch {
            val userInfo = getUserInfoUseCase()
            val userId = (userInfo?.userId ?: -1).toString()
            val profileImg = userInfo?.profileImg
            val profileItems = ProfileItemListDataMapper.toData(fetchProfileItemUseCase(userId))

            _state.value = _state.value.copy(
                profileImage = profileImg,
                totalRankScore = profileItems.totalRankScore,
                profileItems = profileItems.profileItems
            )
        }
    }
}

data class CustomizeProfileScreenState(
    val profileImage: String? = null,
    val totalRankScore: String = "0",
    val profileItems: List<ProfileItemListData.ProfileItemData> = emptyList()
)

object ProfileItemListDataMapper {
    fun toData(response: ProfileItemListModel): ProfileItemListData {
        return with(response) {
            val totalScore = DecimalFormat("#,###").format(totalRankScore)

            ProfileItemListData(
                totalRankScore = totalScore,
                profileItems = profileItems.map { ProfileItemDataMapper.toData(it, 100000) }
            )
        }
    }

    object ProfileItemDataMapper {
        fun toData(response: ProfileItemListModel.ProfileItemModel, totalRankScore: Int): ProfileItemListData.ProfileItemData {
            return with(response) {
                ProfileItemListData.ProfileItemData(
                    customItem = setProfileImage(itemName),
                    isRedPoint = isRedPoint,
                    isUnlocked = updateUnlockState(isUnlocked, itemName, totalRankScore),
                    isUsed = isUsed,
                    itemName = itemName,
                    profileId = profileId
                )
            }
        }

        private fun setProfileImage(itemName: String): Int {
            return when(itemName) {
                "20_000" -> R.drawable.ic_custom_1
                "50_000" -> R.drawable.ic_custom_2
                "100_000" -> R.drawable.ic_custom_3
                "200_000" -> R.drawable.ic_custom_4
                "400_000" -> R.drawable.ic_custom_5
                else -> R.drawable.ic_custom_6
            }
        }

        private fun updateUnlockState(isUnlocked: Boolean, itemName: String, totalRankScore: Int): CustomItemState {
            val itemScore = itemName.replace("_","").toInt()

            return when {
                isUnlocked -> CustomItemState.UNLOCK
                !isUnlocked && totalRankScore >= itemScore -> CustomItemState.UNLOCKABLE
                else -> CustomItemState.LOCK
            }
        }
    }
}

enum class CustomItemState {
    LOCK, UNLOCKABLE, UNLOCK
}

data class ProfileItemListData(
    val totalRankScore: String,
    val profileItems: List<ProfileItemData>
) {
    data class ProfileItemData(
        val customItem: Int,
        val isRedPoint: Boolean,
        var isUnlocked: CustomItemState,
        val isUsed: Boolean,
        val itemName: String,
        val profileId: Int
    )
}
