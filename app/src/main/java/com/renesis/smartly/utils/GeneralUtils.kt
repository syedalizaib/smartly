package com.viewtraksol.kyriopos.utils

import com.renesis.smartly.constant.Constants

class GeneralUtils {

    companion object {
        fun getCategoryID(categoryName: String): String {
            var returningValue: String = ""
            for (catObj in Constants.getCategories()) {
                if (catObj.title.equals(categoryName)) {
                    returningValue = catObj._id
                    break
                }
            }
            return returningValue
        }

    }
}