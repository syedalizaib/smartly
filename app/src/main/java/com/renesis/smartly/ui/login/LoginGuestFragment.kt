package com.renesis.smartly.ui.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.renesis.smartly.R
import com.renesis.smartly.databinding.FragmentLoginGuestBinding
import com.renesis.smartly.entities.User
import com.renesis.smartly.ui.BaseFragment
import com.renesis.smartly.ui.avatarSelection.AvatarSelectionDialog
import com.renesis.smartly.ui.avatarSelection.AvatarSelectionListener

class LoginGuestFragment : BaseFragment(), View.OnClickListener {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginGuestBinding

    private lateinit var loginListeners: LoginListeners

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        val view: ViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login_guest, container, false)
        binding = view as FragmentLoginGuestBinding
        loginViewModel.avatarID.value = 0
        binding.nameText.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                onNameFocusChanged()
            }
        }
        binding.nameText.setOnKeyListener { view, keyCode, event ->

        false
        }
        binding.nameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loginViewModel.name.value = binding.nameText.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        loginViewModel.nameError.observe(viewLifecycleOwner, Observer {
            binding.nameText.error = it
        })
        loginViewModel.avatarID.observe(viewLifecycleOwner, Observer {
            if (it != 0)
                binding.avatar.setImageResource(it)
        })

        binding.btnLogin.setOnClickListener(this)
        binding.avatarLayout.setOnClickListener(this)

        return view.root
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_login -> onLogInClick()
            R.id.avatarLayout -> context?.let { showAvatarSelectionDialog(it) }
        }
    }

    fun hideKeyboard() {
        val imm: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    fun onLogInClick() {
        if (validateName()) {
            if (loginViewModel.avatarID.value != 0) {
                saveUser()
            } else
                context?.let { showAvatarSelectionDialog(it) }
        } else {
            loginViewModel.nameError.value = "Name is required"
            binding.nameText.requestFocus()
        }
    }

    fun onNameFocusChanged() {
        validateName()
    }

    fun saveUser() {
        Log.w("Login_TEST", loginViewModel.name.value + loginViewModel.avatarID.value)
        loginListeners.onLoginSuccess(
            User(
                loginViewModel.name.value.toString(),
                loginViewModel.avatarID.value!!
            )
        )
    }

    private fun validateName(): Boolean {
        return !loginViewModel.name.value.isNullOrEmpty()
    }

    fun showAvatarSelectionDialog(mCtx: Context) {
        val activity = mCtx as Activity
        val ft = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
        val prev = activity.supportFragmentManager.findFragmentByTag("AvatarSelection")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        /*val bundle = Bundle()
        bundle.putSerializable("post_obj", obj)*/
        val dialogFragment = AvatarSelectionDialog()
//        dialogFragment.arguments = bundle
        dialogFragment.setAvatarSelectionListener(object : AvatarSelectionListener {
            override fun onAvatarSelected(avatarID: Int) {
                loginViewModel.avatarID.value = avatarID
                dialogFragment.dismiss()
            }

        })
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_Dialog)
        dialogFragment.show(ft, "AvatarSelection")
    }

    fun setclickListeners(loginListeners: LoginListeners) {
        this.loginListeners = loginListeners;
    }
}