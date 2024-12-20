package com.asap.aljyo.core.components.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.composable.onboarding.SignupState
import com.asap.domain.usecase.user.AuthKakaoUseCase
import com.asap.domain.usecase.user.CacheUserUseCase
import com.asap.domain.usecase.user.CheckCacheUserCase
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authKakaoUseCase: AuthKakaoUseCase,
    private val cacheUserUseCase: CacheUserUseCase,
    private val checkCacheUserCase: CheckCacheUserCase
) : ViewModel() {
    private val _state = MutableStateFlow<RequestState<SignupState>>(RequestState.Initial)
    val state get() = _state.asStateFlow()

    init {
        // 기 로그인 정보 체크
        viewModelScope.launch {
            delay(500)
            val cached = checkCacheUserCase.invoke()
            if (cached) {
                _state.value = RequestState.Success(SignupState.REGISTERED)
            }
        }
    }

    fun kakaoLoginLoading() {
        _state.value = RequestState.Loading
    }

    fun kakaoLoginFailed() {
        _state.value = RequestState.Error()
    }

    fun kakaoLoginSuccess(token: OAuthToken) = viewModelScope.launch {
        Log.d(TAG, "kakaoLoginSuccess")
        authKakaoUseCase.invoke(token.accessToken).catch { _ ->
            _state.value = RequestState.Error()
        }.collect { response ->
            // 서버 토큰 Room DB 저장
            if(response != null) {
                cacheUserUseCase.invoke(response)
                _state.value = RequestState.Success(SignupState.NOT_REGISTERED)
                return@collect
            }
            _state.value = RequestState.Error()
        }
    }

    companion object {
        const val TAG = "OnboardingViewModel"
    }
}