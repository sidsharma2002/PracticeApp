package com.siddharth.practiceapp.data.mapper

import android.util.Log
import com.siddharth.practiceapp.data.dto.feed.HomeFeedDto
import com.siddharth.practiceapp.data.entities.HomeFeed
import com.siddharth.practiceapp.util.Constants.HomeFeedNaming.ANIME
import com.siddharth.practiceapp.util.Constants.HomeFeedNaming.IMDB
import com.siddharth.practiceapp.util.Constants.HomeFeedNaming.JOKES
import com.siddharth.practiceapp.util.Constants.HomeFeedNaming.MARVEL
import com.siddharth.practiceapp.util.Constants.HomeFeedNaming.QUOTES
import com.siddharth.practiceapp.util.Constants.HomeFeedNaming.RICK_MORTY

// should run on background thread
object Mappers {
    fun homeFeedDTOtoEntity(homeFeedDto: HomeFeedDto): MutableList<HomeFeed> {
        val homeFeedList: MutableList<HomeFeed> = mutableListOf()
        var i = 0
        for (feedDto in homeFeedDto.feed) {
            val homeFeed = HomeFeed(dataType = feedDto.dataType)
            when (feedDto.dataType) {
                QUOTES -> {
                    feedDto.author?.let{
                        homeFeed.quotes_author = it
                        }
                        feedDto.content?.let{
                            homeFeed.quotes_content = it
                        }
                }

                JOKES -> {
                    homeFeed.jokes_text = feedDto.text
                    homeFeed.jokes_icon = feedDto.icon
                }

                RICK_MORTY -> {
                    homeFeed.rickAndMortyName = feedDto.name
                    homeFeed.rickAndMortyAvatarImage = feedDto.image
                    homeFeed.rickAndMortyLocation = feedDto.location.name
                    homeFeed.rickAndMortyStatus = feedDto.status as String
                    homeFeed.rickAndMortySpecies = feedDto.species
                }

                IMDB -> {
                    homeFeed.imdbTitle = feedDto.title
                    homeFeed.imdbOverview = feedDto.overview
                    homeFeed.imdbRating = feedDto.imdbRating
                    feedDto.posterURLs?.let {
                        homeFeed.imdbPosterUrl = feedDto.posterURLs.original
                    }
                }

                ANIME -> {
                    homeFeed.animeTitleEn = feedDto.titles.en
                    homeFeed.animeTitleJap = feedDto.titles.jp
                    homeFeed.animeCoverImage = feedDto.cover_image
                    homeFeed.animeScore = feedDto.score
                    feedDto.genres?.let {
                        homeFeed.animeGenre = feedDto.genres[0]
                    }
                    feedDto.trailer_url?.let{
                        homeFeed.animeTrailerLink = feedDto.trailer_url
                    }
                }

                MARVEL -> {
                    feedDto.thumbnail?.let {
                        homeFeed.marvelThumbnailImage =
                            it.path + "." + it.extension
                    }
                    homeFeed.marvelTitle = feedDto.title
                    feedDto.urls?.let {
                        homeFeed.marvelUrl = feedDto.urls[0].url
                    }
                }
            }
            replaceNullWithEmptyData(homeFeed)
            Log.d("HOME FEED $i : ", homeFeed.toString())
            i++
            homeFeedList.add(homeFeed)
        }
        return homeFeedList
    }

    private fun replaceNullWithEmptyData(homeFeed: HomeFeed) {
        if (homeFeed.quotes_author == null) homeFeed.quotes_author = ""
        if (homeFeed.quotes_content == null) homeFeed.quotes_content = ""

        if (homeFeed.jokes_icon == null) homeFeed.jokes_icon = ""
        if (homeFeed.jokes_text == null) homeFeed.jokes_text = ""

        if (homeFeed.rickAndMortyAvatarImage == null) homeFeed.rickAndMortyAvatarImage = ""
        if (homeFeed.rickAndMortyLocation == null) homeFeed.rickAndMortyLocation = ""
        if (homeFeed.rickAndMortyName == null) homeFeed.rickAndMortyName = ""
        if (homeFeed.rickAndMortySpecies == null) homeFeed.rickAndMortySpecies = ""
        if (homeFeed.rickAndMortyStatus == null) homeFeed.rickAndMortyStatus = ""

        if (homeFeed.imdbTitle == null) homeFeed.imdbTitle = ""
        if (homeFeed.imdbRating == null) homeFeed.imdbRating = 0
        if (homeFeed.imdbPosterUrl == null) homeFeed.imdbPosterUrl = ""
        if(homeFeed.imdbOverview == null) homeFeed.imdbOverview = ""

        if (homeFeed.animeTitleEn == null) homeFeed.animeTitleEn = ""
        if (homeFeed.animeTitleJap == null) homeFeed.animeTitleJap = ""
        if (homeFeed.animeCoverImage == null) homeFeed.animeCoverImage = ""
        if (homeFeed.animeScore == null) homeFeed.animeScore = 0
        if (homeFeed.animeGenre == null) homeFeed.animeGenre = ""
        if (homeFeed.animeTrailerLink == null) homeFeed.animeTrailerLink = ""

        if (homeFeed.marvelUrl == null) homeFeed.marvelUrl = ""
        if (homeFeed.marvelThumbnailImage == null) homeFeed.marvelThumbnailImage = ""
        if (homeFeed.marvelTitle == null) homeFeed.marvelTitle = ""
    }
}