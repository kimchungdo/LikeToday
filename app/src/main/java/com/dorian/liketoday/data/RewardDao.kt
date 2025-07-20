package com.dorian.liketoday.data
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RewardDao {
    @Query("SELECT * FROM reward")
    fun getAllRewards(): LiveData<List<Reward>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reward: Reward)

    @Update
    suspend fun update(reward: Reward)
}
