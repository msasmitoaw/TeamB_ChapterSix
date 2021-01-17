package com.suit.team.b.data.db

import androidx.room.*
import com.suit.team.b.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)
    @Query("SELECT * FROM Users WHERE id = :id LIMIT 1")
    fun fetchUserById(id: Int): UserEntity
    @Query("DELETE FROM Users WHERE id = :id")
    fun deleteUserById(id:Int): Int
}
