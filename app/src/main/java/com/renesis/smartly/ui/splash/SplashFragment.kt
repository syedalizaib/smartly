package com.renesis.smartly.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.renesis.smartly.R
import com.renesis.smartly.constant.DelayConstant
import com.renesis.smartly.databinding.FragmentSplashBinding
import com.renesis.smartly.helper.SharedPrefLoginManager
import com.renesis.smartly.ui.BaseFragment

class SplashFragment : BaseFragment() {

    private lateinit var splashViewModel: SplashViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // in here you can do logic when backPress is clicked
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        splashViewModel =
            ViewModelProvider(this, factory).get(SplashViewModel::class.java)
        val view: ViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        binding = view as FragmentSplashBinding

        return view.root
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            goApp()
        }, DelayConstant.SPLASH_DELAY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun goApp() {
        if (context?.let { SharedPrefLoginManager.getInstance(it)?.isLoggedIn } == true)
            navController.navigate(R.id.action_splashFragment_to_homeFragment)
        else
            navController.navigate(R.id.action_splashFragment_to_loginFragment)
    }
}