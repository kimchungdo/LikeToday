package com.dorian.liketoday.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dorian.liketoday.data.AppDatabase
import com.dorian.liketoday.data.Reward
import kotlinx.coroutines.launch

class RewardViewModel(application: Application) : AndroidViewModel(application) {
    private val rewardDao = AppDatabase.getDatabase(application).rewardDao()

    val rewards: LiveData<List<Reward>> = rewardDao.getAllRewards()

    fun insert(reward: Reward) = viewModelScope.launch {
        rewardDao.insert(reward)
    }

    fun update(reward: Reward) = viewModelScope.launch {
        rewardDao.update(reward)
    }
}
