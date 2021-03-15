package com.renesis.smartly.framework.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renesis.smartly.entities.CategoryScore

@Dao
interface CategoryScoreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(categoryScore: ArrayList<CategoryScore>): List<Long>

    @Query("SELECT * FROM CategoryScore")
    fun getCategoryScore(): LiveData<List<CategoryScore>>

    @Query("UPDATE CategoryScore SET score=score+:incVal WHERE id=:catID")
    fun updateCatScore(catID: Int, incVal: Int)

}