package com.renesis.smartly.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AttemptedQuestion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "difficulty")
    val difficulty: String,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "correct_answer")
    val correct_answer: String,
    @ColumnInfo(name = "question_passed")
    val question_passed: Boolean,
) {
    override fun toString() = question
    override fun equals(other: Any?): Boolean {
        return question == (other as AttemptedQuestion).question
    }
}