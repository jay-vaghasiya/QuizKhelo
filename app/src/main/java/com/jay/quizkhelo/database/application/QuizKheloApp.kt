package com.jay.quizkhelo.database.application

import android.app.Application
import androidx.room.Room
import com.jay.quizkhelo.database.AppDatabase
import com.jay.quizkhelo.question.di.QuestionRepository
import com.jay.quizkhelo.user.di.UserRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QuizKheloApp : Application() {

    companion object {
        lateinit var database: AppDatabase
            private set

        lateinit var userRepository: UserRepository
            private set

        lateinit var questionRepository: QuestionRepository
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "user_database"
        ).build()

        userRepository = UserRepository(database.userDao())
        questionRepository = QuestionRepository()

    }
}