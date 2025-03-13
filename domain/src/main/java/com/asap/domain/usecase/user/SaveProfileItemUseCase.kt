package com.asap.domain.usecase.user

import com.asap.domain.repository.UserRepository
import javax.inject.Inject

class SaveProfileItemUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(profileItemId: Int): Boolean {
        return userRepository.saveProfileItem(profileItemId)
    }
}