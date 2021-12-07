package com.siddharth.practiceapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siddharth.practiceapp.data.entities.Todo
import com.siddharth.practiceapp.repository.TodoRepository
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.util.safeCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    val todoHeading = MutableLiveData<String>()
    val todoDesc = MutableLiveData<String>()
    private val _todoList = MutableLiveData<Response<MutableList<Todo>?>>()
    val todoList: LiveData<Response<MutableList<Todo>?>> = _todoList
    private val TAG = this.javaClass.simpleName

    init {
        getTodos()
    }

    fun addTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodo(Todo(heading = todoHeading.value ?: "No Heading", desc = todoDesc.value ?: "No Desc"))
        }
    }

    private fun getTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAllTodos()
            _todoList.postValue(result)
            Log.d(TAG, "" + result.data?.toString() )
        }
    }
}