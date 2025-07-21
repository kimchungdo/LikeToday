package com.dorian.liketoday.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dorian.liketoday.data.AppDatabase
import com.dorian.liketoday.data.WeightEntry
import kotlinx.coroutines.launch

class WeightViewModel(application: Application): AndroidViewModel(application) {
    private val weightDao = AppDatabase.getDatabase(application).weightDao()

    fun getEntriesByDate(date: String): LiveData<List<WeightEntry>> {
        return weightDao.getEntriesByDate(date)
    }

    fun getAllEntries(): LiveData<List<WeightEntry>> {
        return weightDao.getAll()
    }

    fun insert(entry: WeightEntry) = viewModelScope.launch {
        weightDao.insert(entry)
    }
}