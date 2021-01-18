package com.suit.team.b.data.db

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)

    @Query("SELECT id,username,email,password,name FROM Users WHERE username = :username and password = :password LIMIT 1")
    fun checkUserPassword(username: String,password: String): UserEntity

    @Query("SELECT * FROM Users WHERE id = :id LIMIT 1")
    fun fetchUserById(id: Int): UserEntity

    @Query("SELECT * FROM Users WHERE name = :name LIMIT 1")
    fun fetchUserByName(name: String): UserEntity

    @Query("DELETE FROM Users WHERE id = :id")
    fun deleteUserById(id:Int): Int
}