package com.siddharth.practiceapp.data.entities

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "ToDo_table")
data class Todo(
    var heading: String = "",
    var desc: String = "",
    var isCompleted: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val idKey: Long = 0                     //Long type recommend
)

