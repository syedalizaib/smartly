package com.renesis.smartly.constant

import com.renesis.smartly.R
import com.renesis.smartly.entities.Category

class Constants {
    companion object {
        const val SHARED_PREF_NAME = "SMARTLY_APP"
        const val BASE_URL = "https://opentdb.com/"
        const val APP_DB_NAME = "smartly_app_dp"
        const val QUESTION_COUNT = 5
        const val Random_QUESTION_COUNT = 20
        const val QUESTION_TYPE_BOOLEAN = "boolean"
        const val QUESTION_TYPE_NOT_BOOLEAN = "multiple"
        val DIFFICULTY_LEVELS = listOf(
            "easy",
            "medium",
            "hard",
        )
        val AVATARS = listOf(
            R.drawable.avatar1,
            R.drawable.avatar2,
            R.drawable.avatar3,
            R.drawable.avatar4,
            R.drawable.avatar5,
            R.drawable.avatar6,
            R.drawable.avatar7,
            R.drawable.avatar8,
            R.drawable.avatar9,
            R.drawable.avatar10,
            R.drawable.avatar11,
            R.drawable.avatar12,
            R.drawable.avatar13,
            R.drawable.avatar14,
            R.drawable.avatar15,
            R.drawable.avatar16,
            R.drawable.avatar17,
            R.drawable.avatar18,
        )

        fun getCategories() = listOf(
            Category("9", "General Knowledge", R.drawable.ic_gk, R.drawable.grad_rcf_bg, 0),
            Category("17", "Science & Nature", R.drawable.ic_snn, R.drawable.grad_rcf_bg_green, 0),
            Category("20", "Mythology", R.drawable.ic_mythology, R.drawable.grad_rcf_bg_purple, 0),
            Category("21", "Sports", R.drawable.ic_sports, R.drawable.grad_rcf_bg, 0),
            Category("22", "Geography", R.drawable.ic_geography, R.drawable.grad_rcf_bg_green, 0),
            Category("23", "History", R.drawable.ic_history, R.drawable.grad_rcf_bg_purple, 0),
            Category("24", "Politics", R.drawable.ic_politics, R.drawable.grad_rcf_bg, 0),
            Category("25", "Art", R.drawable.ic_art, R.drawable.grad_rcf_bg_green, 0),
            Category(
                "26",
                "Celebrities",
                R.drawable.ic_celebrities,
                R.drawable.grad_rcf_bg_purple,
                0
            ),
            Category("27", "Animals", R.drawable.ic_animals, R.drawable.grad_rcf_bg, 0),
            Category("28", "Vehicles", R.drawable.ic_vehicles, R.drawable.grad_rcf_bg_green, 0),
        )
    }
}