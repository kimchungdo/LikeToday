package com.dorian.liketoday.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeightDao {
    @Query("SELECT * FROM weight_entry WHERE date = :date ORDER BY type")
    fun getEntriesByDate(date: String): LiveData<List<WeightEntry>>

    @Query("SELECT * FROM weight_entry ORDER by date")
    fun getAll(): LiveData<List<WeightEntry>>

    @Query("SELECT * FROM weight_entry WHERE date = :date")
    suspend fun getEntriesByDateDirect(date: String): List<WeightEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(query: WeightEntry)

    @Update
    suspend fun update(entry: WeightEntry)
}