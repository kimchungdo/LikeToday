package com.dorian.liketoday.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="weight_entry")
data class WeightEntry(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val date: String, //yyyy-MM-dd
    val type: String, //morning or evening
    val weight: Float
)