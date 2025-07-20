package com.dorian.liketoday.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val date: String,
    val isDone: Boolean = false
)