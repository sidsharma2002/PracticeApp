package com.siddharth.practiceapp.data.dto.feed

data class Characters(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)