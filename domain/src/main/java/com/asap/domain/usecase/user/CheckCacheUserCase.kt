package com.asap.domain.usecase.user

import com.asap.domain.repository.UserRepository
import javax.inject.Inject

interface CheckCacheUserCase {
    suspend operator fun invoke(): Boolean
}

class CheckCacheUserCaseImpl @Inject constructor(
    private val repository: UserRepository
): CheckCacheUserCase {
    override suspend fun invoke(): Boolean = repository.isCached()
}