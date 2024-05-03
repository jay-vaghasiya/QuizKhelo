package com.jay.quizkhelo.question.di

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.jay.quizkhelo.database.application.QuizKheloApp.Companion.userRepository
import java.io.IOException
import javax.inject.Singleton

@Singleton
class QuestionRepository {
    suspend fun getQuestionList(amount:Int): Any? {

        val response = try {
            QuestionInstance.api.getTriviaQuestions(amount)
        } catch (e: IOException) {
            return e.message
        } catch (e: HttpException) {
            return e.message
        } catch (e: Exception) {
            return e.message
        }
        return response.body() ?: ""
    }
}