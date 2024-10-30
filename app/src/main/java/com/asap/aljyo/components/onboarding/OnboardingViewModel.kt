package com.asap.aljyo.components.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.domain.usecase.KakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase
) : ViewModel() {
    fun requestKakaoLogin() = viewModelScope.launch {
        Log.d("Onboarding viewmodel", "request login")
        kakaoLoginUseCase.invoke().collect {

        }
    }
}