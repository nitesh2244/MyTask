package com.example.mytask

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(context: Context) {
    private val db: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "app-database"
    ).allowMainThreadQueries().build()

    fun addUser(user: User) {
        db.userDao().insertUser(user)
    }

    fun getUserByPassword(password: String): User? {
        return db.userDao().getUserByPassword(password)
    }

    fun getUserByEmail(email: String): User? {
        return db.userDao().getUserByEmail(email)
    }

    fun getAllUsers(): List<User> {
        return db.userDao().getAllUsers()
    }
}
