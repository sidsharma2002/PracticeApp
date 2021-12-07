package com.siddharth.practiceapp.data.dao
import androidx.room.*
import com.siddharth.practiceapp.data.entities.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM ToDo_table")
    suspend fun getAllTodoList(): MutableList<Todo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todo: Todo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodoList(todoList: List<Todo>)

    @Delete
    suspend fun deleteTodo(todo : Todo)

    @Query("DELETE FROM ToDo_table")
    suspend fun deleteAllTodoList()

    @Update(entity = Todo::class)
    fun updateTodo(todo : Todo)
}