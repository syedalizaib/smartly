package com.renesis.smartly.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renesis.smartly.constant.Constants
import com.renesis.smartly.entities.Category
import com.renesis.smartly.entities.CategoryScore
import com.renesis.smartly.entities.User
import com.renesis.smartly.framework.repository.Repository

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    private val _categoryList = MutableLiveData<List<Category>>().apply {
        value = Constants.getCategories()
    }
    val totalScore = MutableLiveData<Int>()
    val categoryList: LiveData<List<Category>> = _categoryList
    val me = MutableLiveData<User>()

    init {
        totalScore.value = 0
    }

    val attemptedQuestionList by lazy {
        repository.getAttemptedQuestions()
    }
    val categoryScoreList by lazy {
        repository.getCategoryScore()
    }

    fun syncCategoryScore() {
        val categoryScoreListToSync: ArrayList<CategoryScore> = ArrayList()
        for (category in Constants.getCategories()) {
            categoryScoreListToSync.add(CategoryScore(category._id.toInt(), 0))
        }
        Thread {
            val categoryScore by lazy {
                repository.addCategoryScore(categoryScoreListToSync)
            }
            Log.w("catscore added ", categoryScore.size.toString())
        }.start()
    }
}