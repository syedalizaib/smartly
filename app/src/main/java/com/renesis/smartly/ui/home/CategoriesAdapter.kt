package com.renesis.smartly.ui.home

import androidx.databinding.ViewDataBinding
import com.renesis.smartly.R
import com.renesis.smartly.base.RecyclerBaseAdapter
import com.renesis.smartly.databinding.ViewListItemCategoryBinding
import com.renesis.smartly.entities.Category
import com.renesis.smartly.entities.CategoryScore


class CategoriesAdapter(var items: List<Category>, private val viewModel: HomeViewModel) :
    RecyclerBaseAdapter() {

    override fun getLayoutIdForPosition(position: Int): Int = R.layout.view_list_item_category

    override fun getViewModel(position: Int): Any? = items[position]

    override fun getItemCount(): Int = items.size


    override fun onViewReady(viewDataBinding: ViewDataBinding, position: Int) {
        super.onViewReady(viewDataBinding, position)

        val binding = viewDataBinding as ViewListItemCategoryBinding
        val item = items[position]

    }

    public fun updateScore(catScoreLisst: List<CategoryScore>) {
        for (item in items) {
            for (catScore in catScoreLisst) {
                if (item._id.equals(catScore.id.toString())) {
                    item.catscore = catScore.score.toInt()
                    notifyDataSetChanged()
                }
            }
        }

    }

}