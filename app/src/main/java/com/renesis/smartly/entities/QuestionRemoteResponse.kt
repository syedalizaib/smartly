package com.renesis.smartly.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class QuestionRemoteResponse(
    @SerializedName("response_code")
    val response_code: Int,
    @SerializedName("results")
    val results: ArrayList<Question>,
) {
    override fun toString() = results.size.toString()
}