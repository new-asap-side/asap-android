package com.asap.domain.repository

interface AuthRepository {
    suspend fun sendFCMToken()
}