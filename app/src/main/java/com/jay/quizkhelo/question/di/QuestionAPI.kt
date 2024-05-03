package com.jay.quizkhelo.question.di

import com.jay.quizkhelo.question.data.Questions
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionAPI {
    @GET("/api.php")
    suspend fun getTriviaQuestions(
        @Query("amount") amount: Int
    ): Response<Questions>
}