package com.asap.aljyo.core.components.viewmodel.main

import androidx.lifecycle.viewModelScope
import com.asap.aljyo.core.components.viewmodel.NetworkViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val _filterState = MutableStateFlow<Filter>(Filter.Total)
    val filterState get() = _filterState.asStateFlow()

    fun filter(select: Filter) {
        viewModelScope.launch { _filterState.emit(select) }
    }

}