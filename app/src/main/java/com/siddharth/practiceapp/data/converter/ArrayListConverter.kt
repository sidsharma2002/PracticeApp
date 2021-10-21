package com.siddharth.practiceapp.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter


class ArrayListConverter {
    @TypeConverter
    fun listFromString(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun listToString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }
}