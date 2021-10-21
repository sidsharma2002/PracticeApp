package com.siddharth.practiceapp.data.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.siddharth.practiceapp.data.dto.News.Article
import com.siddharth.practiceapp.data.dto.News.News

@Keep
@Entity(tableName = "homeData_table")
data class HomeData(
    var type: Int,
    var id: String? = null,
    // var savings: SavingsHomeData? = null,
    // var reminder: Reminder? = null,
    val author: String? = null,
    val content: String? = null,
    @PrimaryKey
    val description: String = "default",
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    var extraString: String? = null
)

data class SavingsHomeData(
    var savings: Int
)

data class Reminder(
    var pendingReminder: String
)