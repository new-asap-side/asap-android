package com.asap.aljyo.components.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.domain.usecase.KakaoLoginUseCase
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<RequestState<Unit?>>(RequestState.Initial)
    val state get() = _state.asStateFlow()

    fun kakaoLoginLoading() {
        _state.value = RequestState.Loading
    }

    fun kakaoLoginFailed() {
        _state.value = RequestState.Error()
    }

    fun kakaoLoginSuccess(token: OAuthToken) = viewModelScope.launch {
        kakaoLoginUseCase.invoke(token = token)
        _state.value = RequestState.Success(null)
    }
}