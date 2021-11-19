package com.siddharth.practiceapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todo_table")
data class ToDo(

    @PrimaryKey(autoGenerate = true)
    var id : Int,
    @ColumnInfo(name = "todo_title")
    var title : String,
    @ColumnInfo(name = "todo_desc")
    var description : String,
    @ColumnInfo(name = "checked")
    var isChecked : Boolean
) : Serializable {

    fun isSelected() : Boolean{
        return isChecked
    }
    fun setSelected(selected: Boolean){
        isChecked = selected
    }
}