package com.asap.aljyo.core.components.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.composable.onboarding.SignupState
import com.asap.domain.usecase.auth.AuthKakaoUseCase
import com.asap.domain.usecase.auth.CacheAuthUseCase
import com.asap.domain.usecase.auth.CheckCachedAuthUseCase
import com.asap.domain.usecase.user.CheckCachedProfileUseCase
import com.asap.domain.usecase.user.FetchUserProfileUseCase
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authKakaoUseCase: AuthKakaoUseCase,
    private val cacheAuthUseCase: CacheAuthUseCase,
    private val checkCachedAuthUseCase: CheckCachedAuthUseCase,
    private val checkProfileUseCase: CheckCachedProfileUseCase,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<RequestState<SignupState>>(RequestState.Initial)
    val state get() = _state.asStateFlow()

    init {
        // 기 로그인 정보 체크
        viewModelScope.launch {
            // auth 정보 유무 체크 (room)
            val cached = checkCachedAuthUseCase()
            if (cached) {
                if (!checkProfileUseCase()) {
                    _state.value = RequestState.Success(SignupState.NOT_REGISTERED)
                    return@launch
                }

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
        _state.value = RequestState.Loading

        authKakaoUseCase.invoke(token.accessToken).catch { e ->
            Log.e(TAG, "AuthKakaoUseCase error - $e")
            handleThrowable(e)
        }.collect { response ->
            if (response == null) {
                _state.value = RequestState.Error()
            }

            // 유저 정보 Room DB 저장
            cacheAuthUseCase(response!!)
            _state.value = RequestState.Success(
                if (response.isJoinedUser) {
                    fetchUserProfileUseCase()
                    SignupState.REGISTERED
                } else {
                    SignupState.NOT_REGISTERED
                }
            )
        }
    }

    private fun handleThrowable(e: Throwable) {
        val errorCode = when (e) {
            is HttpException -> e.code()
            else -> -1
        }
        _state.value = RequestState.Error(errorCode)
    }

    companion object {
        const val TAG = "OnboardingViewModel"
    }
}