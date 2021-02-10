package com.suit.team.b.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Result")
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val key: Int = 0,
    @ColumnInfo(name = "createdAt")
    var createdAt: String,
    @ColumnInfo(name = "_id")
    var id: String,
    @ColumnInfo(name = "message")
    var message: String,
    @ColumnInfo(name = "mode")
    var mode: String,
    @ColumnInfo(name = "result")
    var result: String,
    @ColumnInfo(name = "updatedAt")
    var updatedAt: String
)
