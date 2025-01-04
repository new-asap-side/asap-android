package com.asap.domain.usecase.user

import com.asap.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

interface DeleteUserInfoUseCase {
    suspend operator fun invoke(survey: String): Flow<Boolean?>
}

class DeleteUserInfoUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : DeleteUserInfoUseCase {
    override suspend fun invoke(survey: String): Flow<Boolean?> =
        userRepository.deleteRemoteUserInfo(survey)
            .onCompletion { userRepository.deleteLocalUserInfo() }

}

interface DeleteLocalUserInfoUseCase {
    suspend operator fun invoke()
}

class DeleteLocalUserInfoUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): DeleteLocalUserInfoUseCase {
    override suspend fun invoke() {
        userRepository.deleteLocalUserInfo()
    }

}