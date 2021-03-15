package com.renesis.smartly.framework.remote

import javax.inject.Inject


class ApiDataSource @Inject constructor() : SafeApiRequest() {


    private val TAG = ApiDataSource::class.java.simpleName

    @Inject
    lateinit var apiService: APIService

    suspend fun fetchQuestion(
        amount: String,
        category: String,
        difficulty: String,
        type: String
    ) = getResult { apiService.getQuestion(amount, category, difficulty, type) }

    suspend fun fetchRandomQuestion(
        amount: String
    ) = getResult { apiService.getRandomQuestion(amount) }


}

