package com.renesis.smartly.ui.avatarSelection

import androidx.databinding.ViewDataBinding
import com.renesis.smartly.R
import com.renesis.smartly.base.RecyclerBaseAdapter
import com.renesis.smartly.databinding.ViewListItemAvatarBinding


class AvatarAdapter(var items: List<Int>) : RecyclerBaseAdapter() {

    override fun getLayoutIdForPosition(position: Int): Int = R.layout.view_list_item_avatar

    override fun getViewModel(position: Int): Any? = items[position]

    override fun getItemCount(): Int = items.size


    override fun onViewReady(viewDataBinding: ViewDataBinding, position: Int) {
        super.onViewReady(viewDataBinding, position)

        val binding = viewDataBinding as ViewListItemAvatarBinding
        val item = items[position]

        /*var countText = "${item.itemCount} Item"
        if (item.itemCount > 1)
            countText = "${item.itemCount} Items"
        binding.mtrlListItemCountText.text = countText
        if (item.isChecked) {
            binding.itemLayout.isSelected = true
        }*/
        /*binding.itemLayout.setOnClickListener {
            if (item)
        }*/

    }
}