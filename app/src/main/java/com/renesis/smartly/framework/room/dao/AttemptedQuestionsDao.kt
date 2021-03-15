package com.renesis.smartly.framework.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renesis.smartly.entities.AttemptedQuestion

@Dao
interface AttemptedQuestionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(questionList: List<AttemptedQuestion>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(question: AttemptedQuestion): Long

    @Query("SELECT * FROM AttemptedQuestion ORDER BY id DESC")
    fun getQuestions(): LiveData<List<AttemptedQuestion>>
}