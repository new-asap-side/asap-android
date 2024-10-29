package com.asap.aljyo.components.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.domain.usecase.KakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<RequestState<Unit?>>(RequestState.Initial)
    val state get() = _state.asStateFlow()

    fun requestKakaoLogin() = viewModelScope.launch {
        _state.value = RequestState.Loading
        kakaoLoginUseCase.invoke()
            .catch { e ->
                Log.e("OnboardingViewModel", "$e")
                _state.value = RequestState.Error()
            }
            .collect { response ->
                // Local DB update
                Log.d("OnboardingViewModel", "kakao login response: $response")
                _state.value = RequestState.Success(null)
            }
    }
}