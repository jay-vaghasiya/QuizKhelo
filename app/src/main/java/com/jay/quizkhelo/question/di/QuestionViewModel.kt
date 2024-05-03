package com.jay.quizkhelo.question.di

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.quizkhelo.database.application.QuizKheloApp
import com.jay.quizkhelo.database.application.QuizKheloApp.Companion.userRepository
import com.jay.quizkhelo.question.data.Questions
import com.jay.quizkhelo.user.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    val _userResponseLiveData = MutableLiveData<Questions?>()
    val _errorMessageLiveData = MutableLiveData<String?>()


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun questionList(amount: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            when (
                val userResponse = QuizKheloApp.questionRepository.getQuestionList(amount)
            ) {
                is Questions -> _userResponseLiveData.postValue(userResponse)
                is String -> _errorMessageLiveData.postValue(userResponse)
                else -> {
                    // Do nothing
                }
            }

        }
    }

}