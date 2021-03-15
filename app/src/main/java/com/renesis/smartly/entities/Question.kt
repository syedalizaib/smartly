package com.renesis.smartly.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Question(
    @SerializedName("category")
    val category: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("difficulty")
    val difficulty: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("correct_answer")
    val correct_answer: String,
    @SerializedName("incorrect_answers")
    val incorrect_answers: List<String>,
) {
    override fun toString() = question
    override fun equals(other: Any?): Boolean {
        return question == (other as Question).question
    }
}