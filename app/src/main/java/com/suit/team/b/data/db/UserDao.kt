package com.suit.team.b.data.db

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM Users WHERE id = :id LIMIT 1")
    fun fetchUserById(id: Int): UserEntity

    @Query("DELETE FROM Users WHERE id = :id")
    fun deleteUserById(id: Int): Int

    @Update
    fun updateUser(userEntity: UserEntity): Int
}
