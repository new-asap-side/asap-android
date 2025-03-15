package com.asap.aljyo.core.components.viewmodel.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.asap.aljyo.core.components.viewmodel.NetworkViewModel

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

    fun select(filter: Filter) {
        _filterState.value = filter
    }
}