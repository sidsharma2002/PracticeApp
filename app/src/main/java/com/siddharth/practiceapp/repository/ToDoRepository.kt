package com.siddharth.practiceapp.repository

import androidx.lifecycle.LiveData
import com.siddharth.practiceapp.data.database.ToDoDatabase
import com.siddharth.practiceapp.data.entities.ToDo

class ToDoRepository(val db : ToDoDatabase) {

    suspend fun insertTodo(todo : ToDo){
        db.getTodoDao().insertTodo(todo)
    }

    suspend fun updateTodo(todo: ToDo){
        db.getTodoDao().updateTodo(todo)
    }

    suspend fun deleteAllTodo(){
        db.getTodoDao().deleteAllTodo()
    }

    suspend fun deleteTodo(todo: ToDo){
        db.getTodoDao().deleteTodo(todo)
    }

    fun getAllTodo() : LiveData<List<ToDo>> {
        return db.getTodoDao().getAllTodo()
    }
}