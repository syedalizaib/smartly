package com.renesis.smartly.framework.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.renesis.smartly.constant.Constants
import com.renesis.smartly.entities.AttemptedQuestion
import com.renesis.smartly.entities.CategoryScore
import com.renesis.smartly.framework.room.dao.AttemptedQuestionsDao
import com.renesis.smartly.framework.room.dao.CategoryScoreDao

@Database(
    entities = [
        AttemptedQuestion::class,
        CategoryScore::class
    ], version = 3, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun attemptedQuestionsDao(): AttemptedQuestionsDao
    abstract fun categoryScoreDao(): CategoryScoreDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, Constants.APP_DB_NAME)
                .build()
        }
    }
}