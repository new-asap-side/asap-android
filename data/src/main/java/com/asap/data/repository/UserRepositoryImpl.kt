package com.asap.data.repository

import com.asap.data.local.AppDatabase
import com.asap.data.local.dao.UserDao
import com.asap.data.local.source.TokenDataSource
import com.asap.data.remote.datasource.UserRemoteDataSource
import com.asap.data.remote.response.CheckNicknameResponse
import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.local.User
import com.asap.domain.entity.remote.AuthKakaoResponse
import com.asap.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: AppDatabase,
    private val tokenDataSource: TokenDataSource
) : UserRepository {
    override suspend fun authKakao(kakaoAccessToken: String): Flow<AuthKakaoResponse?> {
        return remoteDataSource.authKakao(kakaoAccessToken)
    }

    override suspend fun isCached(): Boolean {
        val userDao = localDataSource.userDao()
        return userDao.isCached()
    }

    override suspend fun cacheKakaoUserInfo(response: AuthKakaoResponse) {
        val userDao = localDataSource.userDao()
        userDao.insert(
            User(
                userId = response.userId,
                kakaoId = response.kakaoId,
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
            )
        )
        // TokenDataStore 저장
        tokenDataSource.updateAccessToken(response.accessToken)
        tokenDataSource.updateAccessToken(response.refreshToken)
    }

    override suspend fun getUserInfo(): User {
        val dao = localDataSource.userDao()
        return dao.selectAll().first()
    }

    override suspend fun fetchResultCardData(): Flow<ResultCard?> =
        remoteDataSource.resultCard

    override suspend fun checkNickname(nickname: String): Boolean? {
        return remoteDataSource.checkNickname(nickname)?.isPossible
    }
}