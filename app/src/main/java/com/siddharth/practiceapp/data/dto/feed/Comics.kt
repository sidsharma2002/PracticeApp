package com.siddharth.practiceapp.data.dto.feed

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)