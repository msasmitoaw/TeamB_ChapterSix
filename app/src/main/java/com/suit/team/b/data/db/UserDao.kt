package com.suit.team.b.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmark(resultEntity: ResultEntity)

    @Query("SELECT * FROM Result")
    fun fetchBookmark(): MutableList<ResultEntity>

    @Query("DELETE FROM Result WHERE _id = :id")
    fun delBookmark(id: String): Int
}
