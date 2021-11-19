package com.siddharth.practiceapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siddharth.practiceapp.data.entities.ToDo
import com.siddharth.practiceapp.repository.ToDoRepository
import kotlinx.coroutines.launch

class ToDoViewModel(private val todoRepository: ToDoRepository) : ViewModel() {

    fun insertTodo(todo : ToDo) = viewModelScope.launch{
        todoRepository.insertTodo(todo)
    }

    fun updateTodo(todo: ToDo) = viewModelScope.launch {
        todoRepository.updateTodo(todo)
    }

    fun deleteTodo(todo: ToDo) = viewModelScope.launch {
        todoRepository.deleteTodo(todo)
    }

    fun deleteAllTodo() = viewModelScope.launch {
        todoRepository.deleteAllTodo()
    }

    fun getAllTodo() : LiveData<List<ToDo>> {
        return todoRepository.getAllTodo()
    }
}