package com.renesis.smartly.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renesis.smartly.constant.Constants

class SettingsViewModel : ViewModel() {
    val appSounds = MutableLiveData<Boolean>()
    val isQuestionTypeBoolean = MutableLiveData<Boolean>()
    val _difficultyLevels = MutableLiveData<List<String>>().apply {
        value = Constants.DIFFICULTY_LEVELS
    }
}