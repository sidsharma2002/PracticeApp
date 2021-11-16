package com.siddharth.practiceapp.data.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey


@Keep
@Entity(tableName = "HomeFeed_table")
data class HomeFeed (
    var dataType : String = "",
    // for misc Dialog
    var miscDialogHeading : String = "",
    var miscDialogSubheading : String = "",
    // for quotes
    var quotes_author : String = "",
    var quotes_content : String = "",
    // for chuck norris jokes
    var jokes_icon : String = "",
    var jokes_text : String = "",
    // for rick and morty
    var rickAndMortyName : String = "",
    var rickAndMortyStatus : String = "",
    var rickAndMortySpecies : String = "",
    var rickAndMortyLocation : String = "",
    var rickAndMortyAvatarImage : String = "",
    // for imdb stream
    var imdbRating : Int = 0,
    var imdbTitle : String = "",
    val imdbYear : Int = 0,
    var imdbOverview : String = "",
    var imdbPosterUrl : String = "",
    val imdbStreamingPlatform : String = "",
    // for anime
    var animeTitleEn : String = "",
    var animeTitleJap : String = "",
    var animeTrailerLink : String = "",
    val animeTotalEpisodes : Int = 0,
    var animeCoverImage : String = "",
    var animeGenre : String = "",
    var animeScore : Int = 0,
    // for marvel
    var marvelTitle : String = "",
    var marvelUrl : String = "",
    var marvelThumbnailImage : String = "",

    @PrimaryKey(autoGenerate = true)
    val idKey: Long = 0                     //Long type recommend
)
