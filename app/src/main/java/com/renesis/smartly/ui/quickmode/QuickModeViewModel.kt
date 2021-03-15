package com.renesis.smartly.ui.quickmode

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renesis.smartly.R
import com.renesis.smartly.constant.Constants
import com.renesis.smartly.entities.AttemptedQuestion
import com.renesis.smartly.entities.Question
import com.renesis.smartly.entities.QuestionRemoteResponse
import com.renesis.smartly.framework.repository.Repository
import com.viewtraksol.kyriopos.utils.GeneralUtils
import kotlinx.coroutines.*

class QuickModeViewModel(private val repository: Repository) : ViewModel() {

    val loadingBarIsVisible = MutableLiveData<Boolean>()
    val noResult = MutableLiveData<Boolean>()
    val correctIsVisible = MutableLiveData<Boolean>()
    val wrongIsVisible = MutableLiveData<Boolean>()
    val isQuizCompleted = MutableLiveData<Boolean>()
    val isMcq = MutableLiveData<Boolean>()
    val quizLives = MutableLiveData<Int>()
    val quizQuestionsAttempted = MutableLiveData<Int>()
    val questionSeconds = MutableLiveData<Int>()
    val questionList = MutableLiveData<ArrayList<Question>>()
    val currentQuestion = MutableLiveData<Question>()
    val mcqOptionList = MutableLiveData<ArrayList<String>>()
    val correctAnswerAfterAttempt = MutableLiveData<String>()
    val isButtonEnabled = MutableLiveData<Boolean>()
    val answerResultTicking = MutableLiveData<Int>()
    val totalScore = MutableLiveData<Int>()
    val tickerMiliseconds = MutableLiveData<Long>()

    init {
        questionList.value = java.util.ArrayList()
        mcqOptionList.value = java.util.ArrayList()
        loadingBarIsVisible.value = false
        isButtonEnabled.value = true
        correctAnswerAfterAttempt.value = "null"
        tickerMiliseconds.value = 6000
        isQuizCompleted.value = false
        quizQuestionsAttempted.value = 0
        totalScore.value = 0
        quizLives.value = 3
    }

    fun fetchPlayQuestions(inContinuing: Boolean) {
        if (!inContinuing)
            loadingBarIsVisible.value = true
        viewModelScope.launch {
            supervisorScope {
                val questionCall = async {
                    val questionRemoteResponse = repository.getRemoteRandomQuestions(
                        Constants.Random_QUESTION_COUNT.toString()
                    ).data as QuestionRemoteResponse
                    if (questionRemoteResponse.response_code == 0) {
                        questionList.value!!.addAll(questionRemoteResponse.results)
                        if (!inContinuing) {
                            nextQuestion()
                            loadingBarIsVisible.value = false
                        }
                    } else if (questionRemoteResponse.response_code == 1) {
                        noResult.value = true
                    }


                }
            }
        }
    }

    fun nextQuestion() {
        try {
            if (!questionList.value!!.isEmpty()) {
                quizQuestionsAttempted.value = quizQuestionsAttempted.value!! + 1
                correctIsVisible.value = false
                wrongIsVisible.value = false
                correctAnswerAfterAttempt.value = "null"
                currentQuestion.value = questionList.value!!.get(0)
                questionList.value!!.removeAt(0)
                val mol: ArrayList<String>
                mol = ArrayList()
                mol.add(currentQuestion.value!!.correct_answer)
                mol.addAll(currentQuestion.value!!.incorrect_answers)
                mol.shuffle()
                mcqOptionList.value = mol
                setButtonViews()
                isButtonEnabled.value = true
                tickerMiliseconds.value = 6000
                countDownTicker.start()
                if (questionList.value!!.size < 5)
                    fetchPlayQuestions(true)
            } else {
                quizCompletedCall()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

    }

    fun setButtonViews() {
        isMcq.value = !currentQuestion.value!!.type.equals(Constants.QUESTION_TYPE_BOOLEAN)
    }

    fun onClick(v: View) {
        v as AppCompatButton
        countDownTicker.cancel()
        isButtonEnabled.value = false
        if (v.text.equals(currentQuestion.value!!.correct_answer)) {
            saveAttemptedQuestion(currentQuestion.value!!, true)
            incrementScore()
            v.setBackgroundResource(R.drawable.button_correct)
            correctIsVisible.value = true
            nextQuestionTimer.start()
        } else {
            saveAttemptedQuestion(currentQuestion.value!!, false)
            v.setBackgroundResource(R.drawable.button_wrong)
            correctAnswerAfterAttempt.value = currentQuestion.value!!.correct_answer
            wrongIsVisible.value = true
            nextQuestionTimer.start()
            decrementLives()

        }
    }

    val nextQuestionTimer = object : CountDownTimer(4000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            answerResultTicking.value = 3 - (millisUntilFinished / 1000).toInt()
        }

        override fun onFinish() {
            nextQuestion()
        }
    }
    val countDownTicker = object : CountDownTimer(tickerMiliseconds.value!!, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            tickerMiliseconds.value = millisUntilFinished / 1000
            questionSeconds.value = (millisUntilFinished / 1000).toInt()
        }

        override fun onFinish() {
            saveAttemptedQuestion(currentQuestion.value!!, false)
            if (decrementLives())
                nextQuestion()
        }
    }

    fun quizCompletedCall() {
        correctIsVisible.value = false
        wrongIsVisible.value = false
        isQuizCompleted.value = true
        nextQuestionTimer.cancel()
        countDownTicker.cancel()
    }

    fun decrementLives(): Boolean {
        if (quizLives.value!! > 1) {
            quizLives.value = quizLives.value!! - 1
            return true
        } else {
            quizLives.value = quizLives.value!! - 1
            quizCompletedCall()
            return false
        }
    }

    fun incrementScore() {
        var incVal: Int = 0
        when (currentQuestion.value!!.difficulty) {
            Constants.DIFFICULTY_LEVELS.get(0) -> incVal = 1
            Constants.DIFFICULTY_LEVELS.get(1) -> incVal = 2
            Constants.DIFFICULTY_LEVELS.get(2) -> incVal = 3
        }
        totalScore.value = totalScore.value!! + incVal
        updateCatScore(GeneralUtils.getCategoryID(currentQuestion.value!!.category).toInt(), incVal)
    }

    fun saveAttemptedQuestion(question: Question, passed: Boolean) {
        viewModelScope.launch {
            supervisorScope {
                async {
                    withContext(Dispatchers.IO) {
                        val attemptedQuestion = AttemptedQuestion(
                            category = question.category,
                            type = question.type,
                            difficulty = question.difficulty,
                            question = question.question,
                            correct_answer = question.correct_answer,
                            question_passed = passed
                        )
                        Log.w("DB_DATA", repository.addQuestion(attemptedQuestion).toString())
                    }


                }
            }
        }

    }

    fun updateCatScore(catID: Int, score: Int) {
        viewModelScope.launch {
            supervisorScope {
                async {
                    withContext(Dispatchers.IO) {
                        Log.w("DB_DATA", repository.updateCategoryScore(catID, score).toString())
                    }


                }
            }
        }

    }
}