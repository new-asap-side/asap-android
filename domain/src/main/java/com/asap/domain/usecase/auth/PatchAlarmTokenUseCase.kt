package com.asap.domain.usecase.auth

import com.asap.domain.entity.remote.auth.TokenManager
import com.asap.domain.repository.AuthRepository
import javax.inject.Inject

interface PatchAlarmTokenUseCase {
    suspend operator fun invoke(): Boolean
}

class PatchAlarmTokenUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
): PatchAlarmTokenUseCase {
    override suspend fun invoke(): Boolean {
        return repository.patchAlarmToken(TokenManager.fcmToken)
    }
}