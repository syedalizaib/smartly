package com.renesis.smartly.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Category(
    @PrimaryKey val _id: String,
    @SerializedName("category")
    val title: String,
    @SerializedName("catImage")
    val image: Int,
    @SerializedName("catBackground")
    val background: Int,
    @SerializedName("catScore")
    var catscore: Int
) {
    override fun toString() = title
    override fun equals(other: Any?): Boolean {
        return title == (other as Category).title
    }
}