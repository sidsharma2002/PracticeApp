package com.siddharth.practiceapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.siddharth.practiceapp.data.converter.ArrayListConverter
import com.siddharth.practiceapp.data.converter.ArticleConverter
import com.siddharth.practiceapp.data.dao.HomeDataDao
import com.siddharth.practiceapp.data.entities.HomeData

@Database(
    entities = [HomeData::class],
    version = 9
)

// @TypeConverters(ArticleConverter::class, ArrayListConverter::class)
abstract class HomeDataDatabase : RoomDatabase() {
    abstract fun getHomeDataDao(): HomeDataDao
}