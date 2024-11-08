package com.asap.data.repository

import com.asap.data.local.AppDatabase
import com.asap.data.local.source.SessionLocalDataSource
import com.asap.data.remote.datasource.UserRemoteDataSource
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
    private val sessionLocalDataSource: SessionLocalDataSource
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
        sessionLocalDataSource.updateAccessToken(response.accessToken)
        sessionLocalDataSource.updateAccessToken(response.refreshToken)
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

    override suspend fun saveProfile(
        userId: Int,
        nickname: String,
        profileImg: String
    ) {
        // profileImg를 DB에 저장시키면
        remoteDataSource.saveProfile(userId, nickname, profileImg)
            .also {
                localDataSource.userDao().updateProfileImg(it?.profileImageUrl, userId)
            }
    }
}