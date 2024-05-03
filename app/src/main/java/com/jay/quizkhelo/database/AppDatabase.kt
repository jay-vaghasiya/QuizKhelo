package com.jay.quizkhelo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jay.quizkhelo.user.data.ListStringConverter
import com.jay.quizkhelo.user.data.User

@Database(entities = [User::class], version = 1)
@TypeConverters(ListStringConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao():UserDao
}