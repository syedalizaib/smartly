package com.renesis.smartly.ui.home

import androidx.databinding.ViewDataBinding
import com.renesis.smartly.R
import com.renesis.smartly.base.RecyclerBaseAdapter
import com.renesis.smartly.databinding.ViewListItemQuestionBinding
import com.renesis.smartly.entities.AttemptedQuestion


class AttemptedQuestionAdapter(var items: List<AttemptedQuestion>) : RecyclerBaseAdapter() {

    override fun getLayoutIdForPosition(position: Int): Int = R.layout.view_list_item_question

    override fun getViewModel(position: Int): Any? = items[position]

    override fun getItemCount(): Int = items.size


    override fun onViewReady(viewDataBinding: ViewDataBinding, position: Int) {
        super.onViewReady(viewDataBinding, position)
    //can be used for more login handling
        val binding = viewDataBinding as ViewListItemQuestionBinding
        val item = items[position]

    }
}