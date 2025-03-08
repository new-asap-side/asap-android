package com.asap.aljyo.core.components.viewmodel

import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.usecase.group.FetchGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val fetchGroupUseCase: FetchGroupUseCase
) : NetworkViewModel() {
    override val prefix: String = "Search"

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()

    private val _searchState = MutableStateFlow<RequestState<List<AlarmGroup>>>(RequestState.Initial)
    val searchState get() = _searchState.asStateFlow()

    init {
        initialize()
    }

    @OptIn(FlowPreview::class)
    fun initialize() {
        viewModelScope.launch {
            _query.debounce(DEBOUNCE_TIME_OUT).collectLatest {
                if (it.isNotEmpty()) {
                    _searchState.emit(RequestState.Loading)
                    fetchGroupUseCase.searchGroupUseCase(it)
                        .catch { e -> _searchState.emit(handleRequestThrowable(e)) }
                        .collect { result -> _searchState.emit(RequestState.Success(result)) }
                }
            }
        }
    }

    fun onQueryChanged(query: String) {
        viewModelScope.launch {
            _query.emit(query)
        }
    }

    fun search() {
        viewModelScope.launch {
            if (_query.value.isNotEmpty()) {
                _searchState.emit(RequestState.Loading)
                fetchGroupUseCase.searchGroupUseCase(_query.value)
                    .catch { e -> _searchState.emit(handleRequestThrowable(e)) }
                    .collect { result -> _searchState.emit(RequestState.Success(result)) }
            }
        }
    }

    companion object {
        const val DEBOUNCE_TIME_OUT = 500L
    }
}