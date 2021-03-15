package com.renesis.smartly.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.renesis.smartly.R
import com.renesis.smartly.databinding.FragmentLoginChoiceBinding
import com.renesis.smartly.ui.BaseFragment

class LoginChoiceFragment : BaseFragment(), View.OnClickListener {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginChoiceBinding
    private lateinit var clickListener: clickListeners
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
            ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        val view: ViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login_choice, container, false)
        binding = view as FragmentLoginChoiceBinding
        binding.btnFacebookLogin.setOnClickListener(this)
        binding.btnGoogleLogin.setOnClickListener(this)
        binding.btnGuestLogin.setOnClickListener(this)
        return view.root
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_facebook_login -> clickListener.onFacebookLoginClicked()
            R.id.btn_google_login -> clickListener.onGoogleLoginClicked()
            R.id.btn_guest_login -> clickListener.onGuestLoginClicked()
        }
    }

    fun setclickListeners(clickListener: clickListeners) {
        this.clickListener = clickListener;
    }

}