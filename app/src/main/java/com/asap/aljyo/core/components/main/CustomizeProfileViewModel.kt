package com.asap.aljyo.core.components.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.data.remote.response.FetchProfileItemResponse
import com.asap.data.remote.response.ProfileItemResponse
import com.asap.domain.model.ProfileItemListModel
import com.asap.domain.usecase.user.CacheUserProfileUseCase
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
    private val _state = MutableStateFlow<UiState<CustomizeProfileScreenState>>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val userId = getUserInfoUseCase()?.userId ?: -1
            fetchProfileItemUseCase(userId.toString())
        }
    }
}

data class CustomizeProfileScreenState(
    val profileImage: String? = null,
    val totalRankScore: Int = 0,
    val profileItems: List<ProfileItemListData.ProfileItemData> = emptyList()
)

object ProfileItemListDataMapper {
    fun toData(response: ProfileItemListModel): ProfileItemListData {
        return with(response) {
            ProfileItemListData(
                totalRankScore = response.totalRankScore,
                profileItems = response.profileItems.map { ProfileItemDataMapper.toData(it) }
            )
        }
    }

    object ProfileItemDataMapper {
        fun toData(response: ProfileItemListModel.ProfileItemModel): ProfileItemListData.ProfileItemData {
            return with(response) {
                ProfileItemListData.ProfileItemData(
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

data class ProfileItemListData(
    val totalRankScore: Int,
    val profileItems: List<ProfileItemData>
) {
    data class ProfileItemData(
        val isRedPoint: Boolean,
        val isUnlocked: Boolean,
        val isUsed: Boolean,
        val itemName: String,
        val profileId: Int
    )
}
