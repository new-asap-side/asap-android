package com.asap.data.remote.service

import com.asap.domain.entity.ResultCard
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("/")
    suspend fun fetchResultCard(): Response<ResultCard>
}

