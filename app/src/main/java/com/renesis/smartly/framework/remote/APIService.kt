package com.renesis.smartly.framework.remote

import com.renesis.smartly.entities.QuestionRemoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("api.php")
    suspend fun getQuestion(
        @Query("amount") amount: String,
        @Query("category") category: String,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ): Response<QuestionRemoteResponse>

    @GET("api.php")
    suspend fun getRandomQuestion(
        @Query("amount") amount: String
    ): Response<QuestionRemoteResponse>
}