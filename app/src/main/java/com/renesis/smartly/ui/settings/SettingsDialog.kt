package com.renesis.smartly.ui.settings

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.renesis.smartly.R
import com.renesis.smartly.constant.Constants
import com.renesis.smartly.databinding.DialogSettingsBinding
import com.renesis.smartly.helper.SharedPrefLoginManager
import com.renesis.smartly.helper.SharedPrefSettingsManager


class SettingsDialog : DialogFragment(), View.OnClickListener {

    private var mActivity: Activity? = null
    private lateinit var binding: DialogSettingsBinding
    private lateinit var viewModel: SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments == null) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: ViewDataBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_settings,
                container,
                false
            )
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        mActivity = context as Activity?
        binding = view as DialogSettingsBinding
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        subscribeUi()
        return view.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setStyle(STYLE_NO_TITLE, R.style.Theme_Dialog)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        if (dialog == null) return
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        val window = dialog!!.window
        val wlp = window!!.attributes
        mActivity?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        wlp.gravity = Gravity.BOTTOM
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window.attributes = wlp
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> dismiss()
            R.id.btn_logout -> logout()
        }
    }

    private fun subscribeUi() {
        viewModel.appSounds.value = SharedPrefSettingsManager.getInstance(context).isSoundEnabled
        viewModel.isQuestionTypeBoolean.value =
            SharedPrefSettingsManager.getInstance(context).isQuestionBoolean
        when (SharedPrefSettingsManager.getInstance(context).difficultyLevel) {
            Constants.DIFFICULTY_LEVELS.get(0) -> binding.easyRadioButton.isChecked = true
            Constants.DIFFICULTY_LEVELS.get(1) -> binding.mediumRadioButton.isChecked = true
            Constants.DIFFICULTY_LEVELS.get(2) -> binding.hardRadioButton.isChecked = true
        }
        binding.appsoundSwitch.setOnCheckedChangeListener { compoundButton: CompoundButton?, b: Boolean ->
            SharedPrefSettingsManager.getInstance(context).setSoundSettings(b)
        }
        binding.difficultyRadioGroup.setOnCheckedChangeListener { radioGroup: RadioGroup?, i: Int ->
            var dfLevel = Constants.DIFFICULTY_LEVELS.get(0)
            when (i) {
                R.id.easy_radio_button -> dfLevel = Constants.DIFFICULTY_LEVELS.get(0)
                R.id.medium_radio_button -> dfLevel = Constants.DIFFICULTY_LEVELS.get(1)
                R.id.hard_radio_button -> dfLevel = Constants.DIFFICULTY_LEVELS.get(2)
            }
            SharedPrefSettingsManager.getInstance(context).setDifficultyLevel(dfLevel)
        }
        binding.questionTypeRadioGroup.setOnCheckedChangeListener { radioGroup: RadioGroup?, i: Int ->
            var qType = false
            when (i) {
                R.id.mcq_radio_button -> qType = false
                R.id.tf_radio_button -> qType = true
            }
            SharedPrefSettingsManager.getInstance(context).setQuestionBoolean(qType)
        }
        binding.btnClose.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
    }

    private fun logout() {
        dismiss()
        SharedPrefLoginManager.getInstance(context).logout()
    }
}
