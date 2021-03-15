package com.renesis.smartly.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renesis.smartly.framework.repository.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {

    val name = MutableLiveData<String>()
    val avatarID = MutableLiveData<Int>()
    val nameError = MutableLiveData<String>()
}