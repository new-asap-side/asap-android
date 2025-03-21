package com.asap.aljyo.core.components.viewmodel.main

import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.domain.usecase.group.FetchPopularGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val fetchPopularGroupUseCase: FetchPopularGroupUseCase,
): FilterViewModel() {
    override val prefix: String = "Popular"

    fun fetchPopularGroup() {
        viewModelScope.launch(Dispatchers.Default) {
            fetchPopularGroupUseCase()
                .catch { e -> mGroupState.emit(handleThrowable(e)) }
                .collect { popularGroup ->
                    popularGroup?.let {
                        originGroups.emit(it)
                        mGroupState.emit(UiState.Success(it))
                    }
                }
        }
    }
}