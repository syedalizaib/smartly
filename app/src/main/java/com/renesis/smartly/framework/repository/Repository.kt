package com.renesis.smartly.framework.repository

import androidx.lifecycle.LiveData
import com.renesis.smartly.entities.AttemptedQuestion
import com.renesis.smartly.entities.CategoryScore
import com.renesis.smartly.entities.QuestionRemoteResponse
import com.renesis.smartly.framework.Result
import com.renesis.smartly.framework.remote.ApiDataSource
import com.renesis.smartly.framework.room.AppDatabase
import com.renesis.smartly.framework.room.dao.AttemptedQuestionsDao
import com.renesis.smartly.framework.room.dao.CategoryScoreDao
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor() {

    private val TAG = Repository::class.java.simpleName

    @Inject
    lateinit var apiDataSource: ApiDataSource

    @Inject
    lateinit var attemptedQuestionDao: AttemptedQuestionsDao

    @Inject
    lateinit var categoryScoreDao: CategoryScoreDao
    lateinit var db: AppDatabase


    fun addQuestion(question: AttemptedQuestion): Long {
        return attemptedQuestionDao.insert(question)
    }

    fun getAttemptedQuestions(): LiveData<List<AttemptedQuestion>> {
        return attemptedQuestionDao.getQuestions()
    }

    fun getCategoryScore(): LiveData<List<CategoryScore>> {
        return categoryScoreDao.getCategoryScore()
    }

    fun addCategoryScore(categoryScore: ArrayList<CategoryScore>): List<Long> {
        return categoryScoreDao.insertAll(categoryScore)
    }

    fun updateCategoryScore(catID: Int, incVal: Int) {
        return categoryScoreDao.updateCatScore(catID, incVal)
    }

    fun wipeDb() {
        db.clearAllTables()
    }

    suspend fun getRemoteQuestions(
        amount: String,
        category: String,
        difficulty: String,
        type: String
    ): Result<QuestionRemoteResponse> {
        return apiDataSource.fetchQuestion(amount, category, difficulty, type)
    }

    suspend fun getRemoteRandomQuestions(amount: String): Result<QuestionRemoteResponse> {
        return apiDataSource.fetchRandomQuestion(amount)
    }
}
