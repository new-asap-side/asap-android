package com.asap.domain.usecase.user

import com.asap.domain.repository.UserRepository
import javax.inject.Inject

interface CheckCachedProfileUseCase {
    suspend operator fun invoke(): Boolean
}

class CheckCachedProfileUseCaseImpl @Inject constructor(
    private val repository: UserRepository
): CheckCachedProfileUseCase {
    override suspend fun invoke(): Boolean {
        val userInfo = repository.getUserInfo()
        return (userInfo?.nickname != null) && (userInfo.profileImg != null)
    }
}