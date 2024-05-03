package com.jay.quizkhelo.user.di

import androidx.lifecycle.LiveData
import com.jay.quizkhelo.database.UserDao
import com.jay.quizkhelo.user.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insert(user)
        }
    }

    fun getAllUser(): LiveData<List<User>> {
        return userDao.getAllUsers()
    }

    suspend fun updateUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.updateUser(user)
        }
    }

    suspend fun getUserByName(userName: String): User? {
        return userDao.getUserByName(userName)
    }

    suspend fun getUsersSortedByTrueAnswerDesc(): List<User> {
        return userDao.getUsersSortedByTrueAnswerDesc()
    }
}