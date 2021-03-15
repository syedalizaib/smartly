package com.renesis.smartly.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryScore(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "score")
    val score: Long,
) {
    override fun toString() = id.toString() + "-" + score.toString()
}