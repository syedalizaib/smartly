package com.renesis.smartly.ui.quizPlay

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
import com.renesis.smartly.constant.Constants
import com.renesis.smartly.databinding.FragmentQuizPlayBinding
import com.renesis.smartly.framework.repository.Repository
import com.renesis.smartly.helper.SharedPrefSettingsManager
import com.renesis.smartly.ui.BaseFragment
import javax.inject.Inject

class QuizPlayFragment : BaseFragment(), View.OnClickListener {

    private lateinit var viewModel: QuizPlayViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentQuizPlayBinding
    private lateinit var categoryID: String
    private var alive: Boolean = false

    @Inject
    lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.categoryID = arguments?.getString("category_id").toString()
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
        viewModel = ViewModelProvider(this, factory).get(QuizPlayViewModel::class.java)
        val view: ViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_play, container, false)
        binding = view as FragmentQuizPlayBinding
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        subscribeUi()

        return view.root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> navController.navigate(R.id.action_quizPlayFragment_to_homeFragment)
        }
    }

    private fun subscribeUi() {
        viewModel.quizLives.observe(viewLifecycleOwner, Observer {
            binding.livesTxt.text = it.toString()
        })
        viewModel.questionSeconds.observe(viewLifecycleOwner, Observer {
            binding.countdownTxt.text = it.toString()
        })
        viewModel.quizQuestionsAttempted.observe(viewLifecycleOwner, Observer {
            binding.questionCountTxt.text = it.toString()
        })
        viewModel.noResult.observe(viewLifecycleOwner, Observer {
            generalWarning("There are no questions in your current category. Please change your category or question type.")
        })
        viewModel.isMcq.value = true
        viewModel.correctIsVisible.value = false
        viewModel.wrongIsVisible.value = false
        viewModel.loadingBarIsVisible.value = true
        viewModel.categoryID.value = categoryID
        viewModel.difficultyLevel.value =
            SharedPrefSettingsManager.getInstance(context).difficultyLevel
        if (SharedPrefSettingsManager.getInstance(context).isQuestionBoolean)
            viewModel.questionType.value = Constants.QUESTION_TYPE_BOOLEAN
        else
            viewModel.questionType.value = Constants.QUESTION_TYPE_NOT_BOOLEAN

        viewModel.fetchPlayQuestions()
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
                navController.navigate(R.id.action_quizPlayFragment_to_homeFragment)
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
                navController.navigate(R.id.action_quizPlayFragment_to_homeFragment)
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