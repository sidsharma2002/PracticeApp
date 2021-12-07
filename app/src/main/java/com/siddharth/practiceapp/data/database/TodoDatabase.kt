package com.siddharth.practiceapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.siddharth.practiceapp.data.dao.HomeFeedDao
import com.siddharth.practiceapp.data.dao.TodoDao
import com.siddharth.practiceapp.data.entities.HomeFeed
import com.siddharth.practiceapp.data.entities.Todo

@Database(
    entities = [Todo::class],
    version = 1
)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun getTodoDao(): TodoDao
}