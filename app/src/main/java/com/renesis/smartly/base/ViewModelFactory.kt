package com.renesis.smartly.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.renesis.smartly.framework.repository.Repository
import com.renesis.smartly.ui.home.HomeViewModel
import com.renesis.smartly.ui.login.LoginViewModel
import com.renesis.smartly.ui.quickmode.QuickModeViewModel
import com.renesis.smartly.ui.quizPlay.QuizPlayViewModel
import com.renesis.smartly.ui.splash.SplashViewModel
import javax.inject.Inject

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class ViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    @Inject
    lateinit var repository: Repository

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository) as T
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(repository) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository) as T
            modelClass.isAssignableFrom(QuizPlayViewModel::class.java) -> QuizPlayViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(QuickModeViewModel::class.java) -> QuickModeViewModel(
                repository
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
