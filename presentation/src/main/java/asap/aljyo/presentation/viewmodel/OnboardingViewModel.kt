package asap.aljyo.presentation.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.domain.entity.local.UserState
import com.asap.domain.usecase.auth.KakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


data class OnboardingState(
    val isLoading: Boolean = false,
)

sealed class OnboardingEvent {
    data object Profile : OnboardingEvent()

    data object Home : OnboardingEvent()
}

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase
) : ViewModel() {
    private val _event = MutableSharedFlow<OnboardingEvent>()
    val event = _event.asSharedFlow()

    private val _state = MutableStateFlow(OnboardingState())
    val state get() = _state.asStateFlow()

    fun kakaoLogin(context: Context) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            kakaoLoginUseCase(viewModelScope, context).catch { e ->

            }.collect { userState ->
                _state.emit(_state.value.copy(isLoading = false))

                when (userState) {
                    // profile 정보가 없는 경우
                    UserState.NonParticipationUser -> {
                        // navigate to profile
                        _event.emit(OnboardingEvent.Profile)
                    }
                    // profile 정보가 있는 경우 -> 기 가입자
                    UserState.ParticipationUser -> {
                        // navigate to home
                        _event.emit(OnboardingEvent.Home)
                    }
                    null -> {
                        // error
                    }
                }
            }
        }
    }
}