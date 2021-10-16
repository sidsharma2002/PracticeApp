package com.siddharth.practiceapp.data.entities

import com.siddharth.practiceapp.data.dto.News.Article
import com.siddharth.practiceapp.data.dto.News.News

data class HomeData(
    var type: Int,
    var dataOne: SavingsHomeData? = null,
    var reminder: Reminder? = null,
    var news: Article? = null,
    var extraString: String? = null
)

data class SavingsHomeData(
    var savings: Int
)

data class Reminder(
    var pendingReminder: String
)