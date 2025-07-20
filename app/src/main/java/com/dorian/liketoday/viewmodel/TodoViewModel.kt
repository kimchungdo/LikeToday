package com.dorian.liketoday.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dorian.liketoday.data.AppDatabase
import com.dorian.liketoday.data.Todo
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = AppDatabase.getDatabase(application).todoDao()

    fun getTodosByDate(date: String): LiveData<List<Todo>> {
        return todoDao.getTodosByDate(date)
    }

    fun insert(todo: Todo) = viewModelScope.launch {
        todoDao.insert(todo)
    }

    fun update(todo: Todo) = viewModelScope.launch {
        todoDao.update(todo)
    }

    fun delete(todo: Todo) = viewModelScope.launch {
        todoDao.delete(todo)
    }
}
