package com.viewtraksol.kyriopos.utils

import android.content.Context

class ConversionUtils {

    companion object {
        fun dpFromPx(context: Context, px: Float): Float {
            return px / context.resources.displayMetrics.density
        }

        fun pxFromDp(context: Context, dp: Float): Float {
            return dp * context.resources.displayMetrics.density
        }


    }
}