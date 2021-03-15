package com.renesis.smartly.ui.avatarSelection

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.renesis.smartly.R
import com.renesis.smartly.constant.Constants
import com.renesis.smartly.databinding.DialogAvatarSelectionBinding


class AvatarSelectionDialog : DialogFragment(), View.OnClickListener {

    private var mActivity: Activity? = null
    private lateinit var binding: DialogAvatarSelectionBinding
    private lateinit var adapter: AvatarAdapter
    private lateinit var avatarSelectionListener: AvatarSelectionListener
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
//        val view: View = inflater.inflate(R.layout.dialog_avatar_selection, container, false)
        val view: ViewDataBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_avatar_selection,
                container,
                false
            )

        mActivity = context as Activity?
        binding = view as DialogAvatarSelectionBinding
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

        }
    }

    private fun subscribeUi() {
        val mLayoutManager = FlexboxLayoutManager(context, FlexDirection.ROW)
        mLayoutManager.justifyContent = JustifyContent.SPACE_EVENLY
        binding.itemsView.setLayoutManager(mLayoutManager)
        binding.itemsView.setItemAnimator(DefaultItemAnimator())
        adapter = AvatarAdapter(Constants.AVATARS).also {
            it.onItemClick = { index, model ->
                val selectedAvatar = model as Int
                avatarSelectionListener.onAvatarSelected(selectedAvatar)
            }
            it.onItemLongClick = { index, model ->
                /*cateViewModel.multiSelection = true
                cateViewModel.updateSelectedList(model as Category, index)*/
            }
        }
        binding.itemsView.adapter = adapter
//        adapter.notifyDataSetChanged()
    }

    fun setAvatarSelectionListener(avatarSelectionListener: AvatarSelectionListener) {
        this.avatarSelectionListener = avatarSelectionListener;
    }
}
