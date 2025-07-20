package com.dorian.liketoday.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reward")
data class Reward(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val targetStreak: Int,
    val isUnlocked: Boolean = false
)