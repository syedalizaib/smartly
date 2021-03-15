package com.renesis.smartly.helper

import android.content.Context
import android.content.SharedPreferences
import com.renesis.smartly.constant.Constants

class SharedPrefSettingsManager private constructor(private var mCtx: Context) {

    fun setDifficultyLevel(level: String) {
        val sharedPreferences: SharedPreferences =
            Companion.mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_SETTING_DIFFICULTY_LEVEL, level)

        editor.apply()
    }

    val difficultyLevel: String
        get() {
            val sharedPreferences: SharedPreferences =
                Companion.mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(
                KEY_SETTING_DIFFICULTY_LEVEL,
                Constants.DIFFICULTY_LEVELS.get(0)
            )!!
        }

    fun setSoundSettings(value: Boolean) {
        val sharedPreferences: SharedPreferences =
            Companion.mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_SETTING_SOUND, value)

        editor.apply()
    }

    val isSoundEnabled: Boolean
        get() {
            val sharedPreferences: SharedPreferences =
                Companion.mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(KEY_SETTING_SOUND, true)
        }

    fun setQuestionBoolean(value: Boolean) {
        val sharedPreferences: SharedPreferences =
            Companion.mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_SETTING_Q_TYPE, value)

        editor.apply()
    }

    val isQuestionBoolean: Boolean
        get() {
            val sharedPreferences: SharedPreferences =
                Companion.mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(KEY_SETTING_Q_TYPE, false)
        }


    companion object {
        //the constants
        private const val SHARED_PREF_NAME = Constants.SHARED_PREF_NAME
        private const val KEY_SETTING_DIFFICULTY_LEVEL = "KEY_SETTING_DIFFICULTY_LEVEL"
        private const val KEY_SETTING_Q_TYPE = "KEY_SETTING_Q_TYPE"
        private const val KEY_SETTING_SOUND = "KEY_SETTING_SOUND"

        private var mCtx: Context? = null
        private var mInstance: SharedPrefSettingsManager? = null

        @JvmStatic
        @Synchronized
        fun getInstance(context: Context?): SharedPrefSettingsManager {
            this.mCtx = context
            if (mInstance == null) {
                mInstance = SharedPrefSettingsManager(context!!)
            }
            return mInstance as SharedPrefSettingsManager
        }
    }
}