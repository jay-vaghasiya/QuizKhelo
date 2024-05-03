package com.jay.quizkhelo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jay.quizkhelo.user.data.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Query("SELECT * FROM users WHERE name = :userName")
    suspend fun getUserByName(userName: String): User?

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>


    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM users ORDER BY trueAnswer DESC")
    suspend fun getUsersSortedByTrueAnswerDesc(): List<User>
}