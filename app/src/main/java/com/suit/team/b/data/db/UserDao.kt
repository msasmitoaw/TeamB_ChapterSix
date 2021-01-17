package com.suit.team.b.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)
    @Query("SELECT * FROM Users WHERE id = :id LIMIT 1")
    fun fetchUserbyId(id: Int): UserEntity
}
