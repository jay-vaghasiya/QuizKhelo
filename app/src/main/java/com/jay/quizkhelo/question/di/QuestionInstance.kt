package com.jay.quizkhelo.question.di

import com.jay.quizkhelo.util.NetworkModule
import okhttp3.OkHttpClient

object QuestionInstance {
    val api: QuestionAPI by lazy {
        NetworkModule.provideRetrofit(OkHttpClient()).create(QuestionAPI::class.java)
    }
}