package com.siddharth.practiceapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.siddharth.practiceapp.data.dao.ToDoDao
import com.siddharth.practiceapp.data.entities.ToDo

@Database(entities = [ToDo::class], version = 2)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun getTodoDao(): ToDoDao

    companion object {

        @Volatile
        private var instance: ToDoDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ToDoDatabase::class.java,
            "todo_db"
        ).build()
    }
}