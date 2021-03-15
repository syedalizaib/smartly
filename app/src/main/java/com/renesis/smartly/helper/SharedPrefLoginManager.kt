package com.renesis.smartly.helper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.renesis.smartly.constant.Constants
import com.renesis.smartly.entities.User
import com.renesis.smartly.ui.MainActivity

class SharedPrefLoginManager private constructor(private var mCtx: Context) {
    //method to let the user login
    //this method will store the user data in shared preferences
    fun userLogin(user: User) {
        val sharedPreferences: SharedPreferences =
            Companion.mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, user.name)
        editor.putInt(KEY_IMG, user.avatarID)

        editor.apply()
    }


    //this method will checker whether user is already logged in or not
    val isLoggedIn: Boolean
        get() {
            val sharedPreferences: SharedPreferences =
                Companion.mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(KEY_NAME, null) != null
        }

    //this method will give the logged in user
    val user: User
        get() {
            val sharedPreferences: SharedPreferences =
                Companion.mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                sharedPreferences.getString(KEY_NAME, null)!!,
                sharedPreferences.getInt(KEY_IMG, 0)!!
            )
        }

    //this method will logout the user
    fun logout() {
        val sharedPreferences: SharedPreferences =
            Companion.mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        val i = Intent(Companion.mCtx, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Companion.mCtx!!.startActivity(i)
    }

    companion object {
        //the constants
        private const val SHARED_PREF_NAME = Constants.SHARED_PREF_NAME
        private const val KEY_NAME = "keyname"
        private const val KEY_IMG = "keyimg"
        private var mCtx: Context? = null
        private var mInstance: SharedPrefLoginManager? = null

        @JvmStatic
        @Synchronized
        fun getInstance(context: Context?): SharedPrefLoginManager {
            this.mCtx = context
            if (mInstance == null) {
                mInstance = SharedPrefLoginManager(context!!)
            }
            return mInstance as SharedPrefLoginManager
        }
    }
}