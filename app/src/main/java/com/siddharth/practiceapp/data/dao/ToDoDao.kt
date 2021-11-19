package com.siddharth.practiceapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.siddharth.practiceapp.data.entities.ToDo

@Dao
interface ToDoDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo : ToDo)

    @Update()
    suspend fun updateTodo(todo: ToDo)

    @Delete
    suspend fun deleteTodo(todo: ToDo)

    @Query("Delete from todo_table")
    suspend fun deleteAllTodo()

    @Query("Select * from todo_table order by id ASC")
    fun getAllTodo() : LiveData<List<ToDo>>
}