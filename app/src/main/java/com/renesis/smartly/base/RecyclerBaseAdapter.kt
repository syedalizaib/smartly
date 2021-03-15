package com.renesis.smartly.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import com.renesis.smartly.BR

abstract class RecyclerBaseAdapter : RecyclerView.Adapter<RecyclerViewHolder>() {


    var onItemClick: (index: Int, model: Any) -> Unit = { index, model ->

    }

    var onItemLongClick: (index: Int, model: Any) -> Unit = { index, model ->

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        val holder = RecyclerViewHolder(binding)
        binding.lifecycleOwner = holder

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        getViewModel(position)
            ?.let { viewmodel ->
                val bindingSuccess = holder.binding.setVariable(BR.viewmodel, viewmodel)
                if (!bindingSuccess) {
                    throw IllegalStateException("Binding ${holder.binding} viewModel variable name should be 'viewmodel'")
                }

                holder.binding.root.setOnClickListener {
                    onItemClick(position, viewmodel)
                }
                holder.binding.root.setOnLongClickListener {
                    onItemLongClick(position, viewmodel)
                    true
                }
                onViewReady(holder.binding, position)
            }
    }

    open fun onViewReady(viewDataBinding: ViewDataBinding, position: Int) {

    }


    override fun onViewAttachedToWindow(holder: RecyclerViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.markAttach()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.markDetach()
    }


    override fun getItemViewType(position: Int) = getLayoutIdForPosition(position)

    abstract fun getLayoutIdForPosition(position: Int): Int

    abstract fun getViewModel(position: Int): Any?

}

/*
* Base RecyclerView ViewHolder
* */
open class RecyclerViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root),
    LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
    }

    fun markAttach() {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    fun markDetach() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}
