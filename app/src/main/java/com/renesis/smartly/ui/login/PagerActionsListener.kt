package com.renesis.smartly.ui.login

interface PagerActionsListener {

    fun onNextClicked(index: Int)
    fun onSkipClicked(index: Int)
    fun onDoneClicked()

}