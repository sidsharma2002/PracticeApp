package com.siddharth.practiceapp.data.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.siddharth.practiceapp.data.dto.News.Article
import com.siddharth.practiceapp.data.dto.News.News

@Keep
@Entity(tableName = "homeData_table")
data class HomeData(

    @PrimaryKey(autoGenerate = true)    //must be at the end
    val idKey: Long = 0,                     //Long type recommend
    var type: Int,
    var id: String? = "",
    val author: String? = "",
    val content: String? = "",
    val description: String? = "",
    val title: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    var extraString: String? = ""

)

data class SavingsHomeData(
    var savings: Int
)

data class Reminder(
    var pendingReminder: String
)