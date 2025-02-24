package com.asap.aljyo.core.components.viewmodel

import com.asap.domain.usecase.alarm.AlarmOffUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    alarmOffUseCase: AlarmOffUseCase
) : AlarmOffViewModel(alarmOffUseCase) {

}