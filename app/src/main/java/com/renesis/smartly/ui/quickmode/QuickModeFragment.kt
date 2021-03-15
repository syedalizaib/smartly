package com.renesis.smartly.ui.quickmode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.renesis.smartly.R
import com.renesis.smartly.databinding.FragmentQuickModeBinding
import com.renesis.smartly.framework.repository.Repository
import com.renesis.smartly.ui.BaseFragment
import javax.inject.Inject

class QuickModeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var viewModel: QuickModeViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentQuickModeBinding
    private var alive: Boolean = false

    @Inject
    lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // in here you can do logic when backPress is clicked
                if (viewModel.loadingBarIsVisible.value == false)
                    closeWarning()
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
        viewModel = ViewModelProvider(this, factory).get(QuickModeViewModel::class.java)
        val view: ViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_quick_mode, container, false)
        binding = view as FragmentQuickModeBinding
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        subscribeUi()

        return view.root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> navController.navigate(R.id.action_quickmodeFragment_to_homeFragment)
        }
    }

    private fun subscribeUi() {

        viewModel.noResult.observe(viewLifecycleOwner, Observer {
            generalWarning("There are no questions in your current category. Please change your category or question type.")
        })
        viewModel.correctIsVisible.value = false
        viewModel.wrongIsVisible.value = false
        viewModel.loadingBarIsVisible.value = true

        viewModel.fetchPlayQuestions(false)
        binding.btnClose.setOnClickListener(this)
    }

    fun closeWarning() {
        val builder: AlertDialog.Builder
        builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Close")
        builder.setMessage("Do you want to close this quiz?")
        builder.setPositiveButton(
            "Yes"
        ) { dialogInterface, i ->
            if (alive)
                navController.navigate(R.id.action_quickmodeFragment_to_homeFragment)
        }
        builder.setNegativeButton(
            "No"
        ) { dialogInterface, i -> }
        builder.create()
        builder.show()
    }

    fun generalWarning(string: String) {
        val builder: AlertDialog.Builder
        builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setMessage(string)
        builder.setPositiveButton(
            "OK"
        ) { dialogInterface, i ->
            if (alive)
                navController.navigate(R.id.action_quickmodeFragment_to_homeFragment)
        }
        builder.create()
        builder.show()
    }

    override fun onResume() {
        super.onResume()
        alive = true
    }

    override fun onDestroy() {
        alive = false
        super.onDestroy()

    }
}