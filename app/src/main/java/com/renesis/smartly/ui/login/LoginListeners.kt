package com.renesis.smartly.ui.login

import com.renesis.smartly.entities.User

interface LoginListeners {

    fun onLoginSuccess(user: User)
    fun onLoginFailure()

}