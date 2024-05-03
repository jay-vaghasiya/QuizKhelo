package com.jay.quizkhelo.user.di

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.quizkhelo.database.application.QuizKheloApp
import com.jay.quizkhelo.database.application.QuizKheloApp.Companion.userRepository
import com.jay.quizkhelo.user.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(application: Application):AndroidViewModel(application) {

    val allUserLiveData: LiveData<List<User>> = userRepository.getAllUser()

    suspend fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }

    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUser(user)
        }
    }

    suspend fun getUserByName(userName: String): User? {
        return userRepository.getUserByName(userName)
    }

    private val sortedUsersLiveData = MutableLiveData<List<User>>()
    val sortedUsers: LiveData<List<User>> = sortedUsersLiveData

    fun loadSortedUsers() {
        viewModelScope.launch {
            val sortedUsers = userRepository.getUsersSortedByTrueAnswerDesc()
            sortedUsersLiveData.value = sortedUsers
        }
    }
}