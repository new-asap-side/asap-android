package com.asap.data.repository

import com.asap.data.local.AppDatabase
import com.asap.data.remote.UserRemoteDataSource
import com.asap.domain.entity.KakaoLoginResponse
import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.local.User
import com.asap.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: AppDatabase
) : UserRepository {
    override suspend fun kakaoLogin(): Flow<KakaoLoginResponse?> = remoteDataSource.kakaoLogin()

    override suspend fun cacheUserInfo(user: User) {
        val userDao = localDataSource.userDao()
        if (userDao.isCached(user.kakaoId)) {
            return
        }

        userDao.insert(user)
    }

    override suspend fun fetchResultCardData(): Flow<ResultCard?> =
        remoteDataSource.resultCard
}