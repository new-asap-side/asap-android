package com.asap.data.repository

import android.util.Log
import com.asap.data.network.service.LoginService
import javax.inject.Inject
import javax.inject.Singleton

interface LoginRepository {
    suspend fun logIn()
    suspend fun logOut()
}

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRepository {
    override suspend fun logIn() {
        loginService.login()
    }

    override suspend fun logOut() {
        Log.d("LoginReositoryImpl", "log out")
    }

}