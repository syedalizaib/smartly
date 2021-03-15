package com.renesis.smartly.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.renesis.smartly.R
import com.renesis.smartly.databinding.FragmentIntroBinding
import com.renesis.smartly.ui.BaseFragment

class IntroFragment : BaseFragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentIntroBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
            ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        val view: ViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_intro, container, false)
        binding = view as FragmentIntroBinding

        return view.root
    }

}