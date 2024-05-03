package com.jay.quizkhelo.question.data

data class Result(
    val category: String,
    var correct_answer: String,
    val difficulty: String,
    val incorrect_answers: List<String>,
    val question: String,
    val type: String
)