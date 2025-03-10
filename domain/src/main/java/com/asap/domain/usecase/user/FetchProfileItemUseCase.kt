package com.asap.domain.usecase.user

import com.asap.domain.model.ProfileItemListModel
import com.asap.domain.repository.UserRepository
import javax.inject.Inject

class FetchProfileItemUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): ProfileItemListModel{
        return userRepository.fetchProfileItem(userId)
    }
}
