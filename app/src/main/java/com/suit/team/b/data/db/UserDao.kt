package com.suit.team.b.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun markedResult(resultEntity: ResultEntity)

    @Query("SELECT * FROM Result")
    fun fetchMarkedResult(): ResultEntity

}
