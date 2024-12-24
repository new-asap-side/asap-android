package com.asap.aljyo.core.components.group_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.core.components.group_ranking.GroupRankingViewModel.GroupRankingViewModelFactory
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.remote.GroupDetails
import com.asap.domain.usecase.group.FetchGroupDetailsUseCase
import com.asap.domain.usecase.group.JoinGroupUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @AssistedInject constructor(
    private val fetchGroupDetailsUseCase: FetchGroupDetailsUseCase,
    private val joinGroupUseCase: JoinGroupUseCase,
    @Assisted private val groupId: Int
) : ViewModel() {
    private val _groupDetailsState = MutableStateFlow<UiState<GroupDetails>>(UiState.Loading)
    val groupDetails get() = _groupDetailsState.asStateFlow()

    private val _joinGroupState = MutableStateFlow<RequestState<Boolean>>(RequestState.Initial)
    val joinGroupState get() = _joinGroupState


    init {
        viewModelScope.launch {
            fetchGroupDetailsUseCase.invoke(groupId = groupId).catch {

            }.collect {

            }
        }
    }

    fun joinGroup(body: Map<String, Any>) = viewModelScope.launch {
        joinGroupUseCase.invoke(body).catch { e ->
            // error
            _joinGroupState.value = RequestState.Error(errorCode = e.toString())
        }.collect { result ->
            if (result != null) {
                _joinGroupState.value = RequestState.Success(result)
            }
        }
    }

    @AssistedFactory
    interface GroupDetailsViewModelFactory {
        fun create(groupId: Int): GroupDetailsViewModel
    }

    companion object {
        private const val TAG = "GroupDetailsViewModel"

        @Suppress("UNCHECKED_CAST")
        fun provideGroupDetailsViewModelFactory(
            factory: GroupDetailsViewModelFactory,
            groupId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                factory.create(groupId = groupId) as T
        }
    }
}