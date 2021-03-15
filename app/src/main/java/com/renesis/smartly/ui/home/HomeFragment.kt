package com.renesis.smartly.ui.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.renesis.smartly.R
import com.renesis.smartly.constant.Constants
import com.renesis.smartly.constant.DelayConstant
import com.renesis.smartly.databinding.FragmentHomeBinding
import com.renesis.smartly.entities.Category
import com.renesis.smartly.helper.SharedPrefLoginManager
import com.renesis.smartly.ui.BaseFragment
import com.renesis.smartly.ui.settings.SettingsDialog

class HomeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var viewModel: HomeViewModel
    private var mBackPressed: Long = 0
    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var attemptedQuestionAdapter: AttemptedQuestionAdapter
    private lateinit var mCtx: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // in here you can do logic when backPress is clicked
                if (mBackPressed + DelayConstant.CLOSE_DELAY > System.currentTimeMillis()) {
                    activity!!.finish()
                } else {
                    showToastShort(getString(R.string.exit_toast))
                }

                mBackPressed = System.currentTimeMillis()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        val view: ViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding = view as FragmentHomeBinding
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        subscribeUi()
        return view.root
    }

    private fun subscribeUi() {
        viewModel.me.value = SharedPrefLoginManager.getInstance(context).user
        viewModel.categoryList.observe(viewLifecycleOwner, Observer {
            categoriesAdapter = CategoriesAdapter(it, viewModel).also {
                it.onItemClick = { index, model ->
                    val categoryItem = model as Category
                    val bundle = bundleOf("category_id" to categoryItem._id)
                    navController.navigate(
                        R.id.action_homeFragment_to_quizPlayFragment,
                        bundle
                    )
                }
                it.onItemLongClick = { index, model ->
                }
            }
            binding.itemsView.adapter = categoriesAdapter
            categoriesAdapter.notifyDataSetChanged()
            viewModel.categoryScoreList.observe(viewLifecycleOwner, Observer {
                if (it.size != Constants.getCategories().size) {
                    viewModel.syncCategoryScore()
                }
                categoriesAdapter.updateScore(it)
                var tempCount: Int = 0
                for (catScore in it) {
                    tempCount = tempCount + catScore.score.toInt()
                }
                viewModel.totalScore.value = tempCount
            })
        })
        viewModel.me.observe(viewLifecycleOwner, Observer {
            binding.avatar.setImageResource(it.avatarID)
            binding.title.text = it.name
        })
        viewModel.totalScore.observe(viewLifecycleOwner, Observer {
            binding.scoreText.text = "Total Score: " + it
        })
        viewModel.attemptedQuestionList.observe(viewLifecycleOwner, Observer {
            attemptedQuestionAdapter = AttemptedQuestionAdapter(it)
            binding.questionItemsView.adapter = attemptedQuestionAdapter
            if (it.size > 0)
                binding.titleLatestQList.visibility = View.VISIBLE
        })

        binding.btnSetting.setOnClickListener(this)
        binding.btnQuickMode.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_setting -> context?.let { showSettingsDialog(it) }
            R.id.btn_burgermenu -> showToastShort("Burger Menu Button")
            R.id.btn_quick_mode -> navController.navigate(R.id.action_homeFragment_to_quickmodeFragment)
        }
    }

    fun showSettingsDialog(mCtx: Context) {
        val activity = mCtx as Activity
        val ft = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
        val prev = activity.supportFragmentManager.findFragmentByTag("Settings")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        val dialogFragment = SettingsDialog()
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_Dialog)
        dialogFragment.show(ft, "Settings")
    }

}