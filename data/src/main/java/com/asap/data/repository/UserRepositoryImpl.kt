package com.asap.data.repository

import com.asap.data.local.AppDatabase
import com.asap.data.remote.datasource.UserRemoteDataSource
import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.local.KakaoUser
import com.asap.domain.repository.UserRepository
import com.kakao.sdk.auth.model.OAuthToken
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: AppDatabase
) : UserRepository {
    override suspend fun isCached(): Boolean {
        val userDao = localDataSource.userDao()
        return userDao.isCached()
    }

    override suspend fun cacheKakaoUserInfo(token: OAuthToken) {
        val userDao = localDataSource.userDao()
        userDao.insert(
            KakaoUser(
                accessToken = token.accessToken,
                refreshToken = token.refreshToken,
                idToken = token.idToken
            )
        )
    }

    override suspend fun fetchResultCardData(): Flow<ResultCard?> =
        remoteDataSource.resultCard
}