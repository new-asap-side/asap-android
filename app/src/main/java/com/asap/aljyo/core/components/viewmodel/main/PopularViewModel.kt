package com.asap.aljyo.core.components.viewmodel.main

import androidx.lifecycle.viewModelScope
import com.asap.aljyo.core.components.viewmodel.NetworkViewModel
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.usecase.group.FetchPopularGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val fetchPopularGroupUseCase: FetchPopularGroupUseCase,
): FilterViewModel() {
    override val prefix: String = "Popular"

    private val _popularGroupState = MutableStateFlow<UiState<List<AlarmGroup>>>(UiState.Loading)
    val popularGroupState get() = _popularGroupState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchPopularGroupUseCase()
                .catch { e -> _popularGroupState.value = handleThrowable(e) }
                .collect { popularGroup ->
                    _popularGroupState.emit(UiState.Success(popularGroup ?: emptyList()))
                }
        }
    }
}