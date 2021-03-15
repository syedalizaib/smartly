package com.renesis.smartly.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.renesis.smartly.App
import com.renesis.smartly.R
import com.renesis.smartly.base.ViewModelFactory
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
    protected var isTablet: Boolean = false

    @Inject
    lateinit var factory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun showToastLong(msg: String) {
        context?.let {
            Toast.makeText(it, msg, Toast.LENGTH_LONG).show()
        }
    }

    fun showToastShort(msg: String) {
        context?.let {
            Toast.makeText(it, msg, Toast.LENGTH_SHORT).show()
        }
    }

    fun showSnackbar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.getAppComponent().inject(this)
        isTablet = context.resources.getBoolean(R.bool.isTablet)
    }
    /* protected fun loadFragment(fragment: Fragment?) {
         val manager = activity!!.supportFragmentManager
         if (manager.backStackEntryCount > 0) manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
         val transactionS = manager.beginTransaction()
         transactionS.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
         transactionS.replace(R.id.container, fragment!!, "freg")
         transactionS.addToBackStack(null)
         transactionS.commit()
     }*/
}