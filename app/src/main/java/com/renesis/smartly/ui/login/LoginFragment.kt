package com.renesis.smartly.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.renesis.smartly.R
import com.renesis.smartly.databinding.FragmentLoginBinding
import com.renesis.smartly.entities.User
import com.renesis.smartly.helper.SharedPrefLoginManager
import com.renesis.smartly.ui.BaseFragment

class LoginFragment : BaseFragment(), clickListeners, View.OnClickListener, LoginListeners {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    public lateinit var loginChoiceFragment: LoginChoiceFragment
    public lateinit var loginGuestFragment: LoginGuestFragment
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

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
        loginViewModel =
            ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        val view: ViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding = view as FragmentLoginBinding
        loginGuestFragment = LoginGuestFragment()
        loginGuestFragment.setclickListeners(this)

        loginChoiceFragment = LoginChoiceFragment()
        loginChoiceFragment.setclickListeners(this)

        binding.viewPager.adapter =
            LoginAdapter(childFragmentManager, loginChoiceFragment, loginGuestFragment)
        binding.actionNext.setOnClickListener(this)
        binding.actionBack.setOnClickListener(this)
        return view.root
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.action_back -> if (binding.viewPager.currentItem > 0) {
                binding.viewPager.currentItem--
                navButtonCheck(binding.viewPager.currentItem)
            }
            R.id.action_next -> if (binding.viewPager.currentItem < 3) {
                binding.viewPager.currentItem++
                navButtonCheck(binding.viewPager.currentItem)
            }
        }
    }

    private fun navButtonCheck(currentItem: Int) {
        if (currentItem > 0) {
            binding.actionBack.visibility = View.VISIBLE
            binding.actionNext.visibility = View.INVISIBLE
        } else {
            binding.actionBack.visibility = View.INVISIBLE
            binding.actionNext.visibility = View.VISIBLE
        }
    }

    class LoginAdapter(
        fm: FragmentManager,
        private val loginChoiceFragment: LoginChoiceFragment,
        private val loginGuestFragment: LoginGuestFragment
    ) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {

            return when (position) {
                0 -> IntroFragment()
                1 -> this.loginChoiceFragment
                2 -> this.loginGuestFragment

                else -> throw  IllegalStateException("Fragment is not included in adapter")
            }
        }

        override fun getCount(): Int = 3

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "WELCOME!"
                1 -> "LOGIN"
                2 -> "CONTINUE AS GUEST"
                else -> "Screen #"
            }
        }
    }

    override fun onFacebookLoginClicked() {
        showToastShort("Facebook Login")
    }

    override fun onGoogleLoginClicked() {
        showToastShort("Google Login")
    }

    override fun onGuestLoginClicked() {
        binding.viewPager.currentItem++
        navButtonCheck(binding.viewPager.currentItem)
    }

    override fun onLoginSuccess(user: User) {
        Log.w("Login_TEST", user.toString())
        SharedPrefLoginManager.getInstance(context).userLogin(user)
        navController.navigate(R.id.action_loginFragment_to_homeFragment)
    }

    override fun onLoginFailure() {

    }
}