package com.siddharth.practiceapp.data.entities

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dataType)
        parcel.writeString(miscDialogHeading)
        parcel.writeString(miscDialogSubheading)
        parcel.writeString(quotes_author)
        parcel.writeString(quotes_content)
        parcel.writeString(jokes_icon)
        parcel.writeString(jokes_text)
        parcel.writeString(rickAndMortyName)
        parcel.writeString(rickAndMortyStatus)
        parcel.writeString(rickAndMortySpecies)
        parcel.writeString(rickAndMortyLocation)
        parcel.writeString(rickAndMortyAvatarImage)
        parcel.writeInt(imdbRating)
        parcel.writeString(imdbTitle)
        parcel.writeInt(imdbYear)
        parcel.writeString(imdbOverview)
        parcel.writeString(imdbPosterUrl)
        parcel.writeString(imdbStreamingPlatform)
        parcel.writeString(animeTitleEn)
        parcel.writeString(animeTitleJap)
        parcel.writeString(animeTrailerLink)
        parcel.writeInt(animeTotalEpisodes)
        parcel.writeString(animeCoverImage)
        parcel.writeString(animeGenre)
        parcel.writeInt(animeScore)
        parcel.writeString(marvelTitle)
        parcel.writeString(marvelUrl)
        parcel.writeString(marvelThumbnailImage)
        parcel.writeLong(idKey)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeFeed> {
        override fun createFromParcel(parcel: Parcel): HomeFeed {
            return HomeFeed(parcel)
        }

        override fun newArray(size: Int): Array<HomeFeed?> {
            return arrayOfNulls(size)
        }
    }
}
