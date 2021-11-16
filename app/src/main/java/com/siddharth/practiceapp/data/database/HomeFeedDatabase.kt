package com.siddharth.practiceapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.siddharth.practiceapp.data.dao.HomeFeedDao
import com.siddharth.practiceapp.data.entities.HomeFeed

@Database(
    entities = [HomeFeed::class],
    version = 15
)

// @TypeConverters(ArticleConverter::class, ArrayListConverter::class)
abstract class HomeFeedDatabase : RoomDatabase() {
    abstract fun getHomeFeedDao(): HomeFeedDao
}