package com.asap.aljyo.core.components.group_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.domain.usecase.group.JoinGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val joinGroupUseCase: JoinGroupUseCase
) : ViewModel() {
    private val _joinGroupState = MutableStateFlow<RequestState<Boolean>>(RequestState.Initial)
    val joinGroupState get() = _joinGroupState

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
}