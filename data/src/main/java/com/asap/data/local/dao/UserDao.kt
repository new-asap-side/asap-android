package com.asap.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asap.domain.entity.local.User

@Dao
interface UserDao {
    @Query("SELECT EXISTS(SELECT * FROM User)")
    suspend fun isCached(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg user: User)

    @Query("UPDATE User SET `profile_image` = :profileImg WHERE user_id = :userId")
    suspend fun updateProfileImg(profileImg: String?, userId: Int)

    @Query("UPDATE User SET nickname = :nickname WHERE user_id = :userId")
    suspend fun updateNickname(nickname: String, userId: Int)

    @Query("UPDATE User SET access_token = :accessToken WHERE user_id = :userId")
    suspend fun updateAccessToken(accessToken: String, userId: Int)

    @Query("UPDATE User SET refresh_token = :refreshToken WHERE user_id = :userId")
    suspend fun updateRefreshToken(refreshToken: String, userId: Int)

    @Query("UPDATE User SET `profile_item` = :profileItem WHERE user_id = :userId")
    suspend fun updateProfileItem(profileItem: String?, userId: Int)

    @Query("SELECT * FROM User")
    suspend fun selectAll(): List<User>

    @Query("DELETE FROM User")
    suspend fun deleteAll()
}