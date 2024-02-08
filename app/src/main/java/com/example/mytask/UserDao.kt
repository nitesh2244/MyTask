package com.example.mytask

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE password = :password")
    fun getUserByPassword(password: String): User?

    @Query("SELECT * FROM users WHERE name = :name")
    fun getUserByName(name: String): User?

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>
}
