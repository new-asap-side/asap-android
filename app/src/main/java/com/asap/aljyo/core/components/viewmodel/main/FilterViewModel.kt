package com.asap.aljyo.core.components.viewmodel.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.core.components.viewmodel.NetworkViewModel
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.remote.AlarmGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class Filter {
    data object Total: Filter()

    data object Public: Filter()

    data object Private: Filter()
}

abstract class FilterViewModel(
    override val prefix: String = "Filter"
) : NetworkViewModel() {
    private val _filterState = mutableStateOf<Filter>(Filter.Total)
    val filterState: State<Filter> get() = _filterState

    protected val originGroups = MutableStateFlow<List<AlarmGroup>>(emptyList())
    protected val mGroupState = MutableStateFlow<UiState<List<AlarmGroup>>>(UiState.Loading)
    val groupState get() = mGroupState.asStateFlow()

    fun filter(filter: Filter) {
        _filterState.value = filter
        viewModelScope.launch {
            originGroups.run {
                when (filter) {
                    Filter.Private -> {
                        mGroupState.emit(
                            UiState.Success(
                                value.filter { !it.isPublic }
                            )
                        )
                    }

                    Filter.Public -> {
                        mGroupState.emit(
                            UiState.Success(
                                value.filter { it.isPublic }
                            )
                        )
                    }
                    Filter.Total -> {
                        mGroupState.emit(UiState.Success(value))
                    }
                }
            }
        }
    }
}