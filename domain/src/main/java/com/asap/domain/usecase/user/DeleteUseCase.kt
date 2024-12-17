package com.asap.domain.usecase.user

import com.asap.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DeleteUseCase {
    suspend operator fun invoke(survey: String): Flow<Unit>
}

class DeleteUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): DeleteUseCase {
    override suspend fun invoke(survey: String): Flow<Unit> {
        return userRepository.delteUserInfo(survey)
    }

}