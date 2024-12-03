package com.asap.domain.usecase.user

import android.util.Log
import com.asap.domain.entity.remote.Alarm
import com.asap.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchAlarmListUseCase {
    suspend operator fun invoke(): Flow<List<Alarm>?>
}

class FetchAlarmListUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): FetchAlarmListUseCase {
    override suspend fun invoke(): Flow<List<Alarm>?> {
        Log.d("FetchAlarmListUseCaseImpl", "invoke()")
        return userRepository.fetchUserAlarmList()
    }

}