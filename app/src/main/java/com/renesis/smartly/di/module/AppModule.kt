package com.renesis.smartly.di.module

import android.content.Context
import com.renesis.smartly.App
import com.renesis.smartly.framework.room.AppDatabase
import com.renesis.smartly.framework.room.dao.AttemptedQuestionsDao
import com.renesis.smartly.framework.room.dao.CategoryScoreDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule(context: App) {

    private val mContext = context

    @Singleton
    @Provides
    open fun provideContext(): Context {
        return mContext
    }

    @Singleton
    @Provides
    fun provideAttemptedQuestionsDao(): AttemptedQuestionsDao {
        return provideRoom().attemptedQuestionsDao()
    }

    @Singleton
    @Provides
    fun provideCategoryScoreDao(): CategoryScoreDao {
        return provideRoom().categoryScoreDao()
    }

    @Singleton
    @Provides
    fun provideRoom(): AppDatabase {
        return AppDatabase.getInstance(mContext)
    }

}