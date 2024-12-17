package com.asap.domain.usecase.user

import com.asap.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

interface DeleteUserInfoUseCase {
    suspend operator fun invoke(survey: String): Flow<Unit>
}

class DeleteUserInfoUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : DeleteUserInfoUseCase {
    override suspend fun invoke(survey: String): Flow<Unit> =
        userRepository.deleteRemoteUserInfo(survey)
            .onCompletion { userRepository.deleteLocalUserInfo() }

}