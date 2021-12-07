package com.siddharth.practiceapp.repository

import com.siddharth.practiceapp.data.dao.TodoDao
import com.siddharth.practiceapp.data.entities.Todo
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.util.safeCall
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val dao: TodoDao
) {
    suspend fun getAllTodos(): Response<MutableList<Todo>?> =
        safeCall {
            val result = dao.getAllTodoList()
            Response.Success(result)
        }

    suspend fun addTodo(todo: Todo) {
        dao.insertTodo(todo)
    }
}