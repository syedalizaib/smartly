package com.renesis.smartly.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @SerializedName("name")
    val name: String,
    @SerializedName("avatarID")
    val avatarID: Int,
) {
    override fun toString() = name

}