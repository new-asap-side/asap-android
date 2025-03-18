package com.asap.aljyo.core.components.viewmodel.main

import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.domain.usecase.group.FetchLatestGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestViewModel @Inject constructor(
    private val fetchLatestGroupUseCase: FetchLatestGroupUseCase,
) : FilterViewModel() {
    override val prefix: String = "Latest"

    init {
        fetchLatestGroup()
    }

    private fun fetchLatestGroup() {
        viewModelScope.launch {
            fetchLatestGroupUseCase()
                .catch { e ->
                    mGroupState.emit(handleThrowable(e))
                }
                .collect { result ->
                    result?.let {
                        originGroups.emit(it)
                        mGroupState.emit(UiState.Success(it))
                    }
                }
        }
    }
}