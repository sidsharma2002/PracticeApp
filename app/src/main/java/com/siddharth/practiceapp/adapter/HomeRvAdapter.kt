package com.siddharth.practiceapp.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.data.entities.HomeFeed
import com.siddharth.practiceapp.interfaces.OnItemClickListener
import com.siddharth.practiceapp.util.Constants
import com.siddharth.practiceapp.util.slideUp

class HomeRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        @JvmStatic
        val quotesType = 1

        @JvmStatic
        val reminderType = 2

        @JvmStatic
        val miscDialogType = 3

        @JvmStatic
        val rickAndMortyType = 4

        @JvmStatic
        val jokesType = 5

        @JvmStatic
        val marvelsType = 6

        @JvmStatic
        val imdbType = 7

        @JvmStatic
        val animeType = 8
    }

    val dataList = ArrayList<HomeFeed>()

    class QuotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            Log.d("QuotesHolder", "created")
        }

        private val quotesHeadline: TextView = itemView.findViewById(R.id.tv_quotesHeadline)
        private val quotesDescription: TextView = itemView.findViewById(R.id.tv_quotesDesc)

        fun setData(data: HomeFeed, holder: QuotesHolder) {
            holder.quotesHeadline.text = data.quotes_content
            holder.quotesDescription.text = data.quotes_author
        }
    }

    class JokesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val jokeContent: TextView = itemView.findViewById(R.id.tv_jokeContent)
        private val jokeIcon: ImageView = itemView.findViewById(R.id.iv_icon)

        fun setData(data: HomeFeed, holder: JokesHolder) {
            holder.jokeContent.text = data.jokes_text
            Glide.with(holder.itemView.context).load(data.jokes_icon).circleCrop()
                .into(holder.jokeIcon)
        }
    }

    class RickMortyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tv_rickmortyName)
        private val desc: TextView = itemView.findViewById(R.id.tv_rickmortyDesc)
        private val avatar: ImageView = itemView.findViewById(R.id.iv_rickmortyAvatar)

        fun setData(data: HomeFeed, holder: RickMortyHolder) {
            holder.name.text = data.rickAndMortyName
            holder.desc.text = "Status : " +
                    data.rickAndMortyStatus + "\n" + "Species : " + data.rickAndMortySpecies + "\n" + "Location : " + data.rickAndMortyLocation
            Glide.with(holder.itemView.context).load(data.rickAndMortyAvatarImage)
                .circleCrop().into(holder.avatar)
        }
    }

    class MarvelHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById(R.id.iv_marvelThumbnail)
        val cardView: CardView = itemView.findViewById(R.id.marvel_cardView)
        fun setData(data: HomeFeed, holder: MarvelHolder) {
            Glide.with(holder.itemView.context)
                .load(data.marvelThumbnailImage)
                .apply(
                    RequestOptions().dontTransform() // this line
                )
                .into(holder.thumbnail)
            ViewCompat.setTransitionName(holder.cardView, data.marvelThumbnailImage)
        }

        fun createCircularRevealEffect(holder: MarvelHolder) {
            val myView = holder.cardView
            myView.isVisible = false
            val cx = myView.width / 2
            val cy = myView.height / 2
            // get the final radius for the clipping circle
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
            // create the animator for this view (the start radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, finalRadius)
            // make the view visible and start the animation
            myView.visibility = View.VISIBLE
            anim.apply {
                duration = 1000
                start()
            }
        }
    }

    class ImdbHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tv_imdbName)
        private val desc: TextView = itemView.findViewById(R.id.tv_imdbDesc)
        private val rating: TextView = itemView.findViewById(R.id.tv_imdbRating)
        private val thumbnail: ImageView = itemView.findViewById(R.id.iv_imdbThumbnail)

        fun setData(data: HomeFeed, holder: ImdbHolder) {
            holder.name.text = data.imdbTitle
            holder.desc.text = data.imdbOverview
            holder.rating.text = data.imdbRating.toString()
            Glide.with(holder.itemView.context).load(data.imdbPosterUrl)
                .centerCrop()
                .into(holder.thumbnail)

        }
    }

    class AnimeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameEng: TextView = itemView.findViewById(R.id.tv_animeNameEng)
        private val nameJap: TextView = itemView.findViewById(R.id.tv_animeNameJap)
        private val desc: TextView = itemView.findViewById(R.id.tv_animeDesc)
        private val rating: TextView = itemView.findViewById(R.id.tv_animeRating)
        private val thumbnail: ImageView = itemView.findViewById(R.id.iv_animeThumbnail)
        private val playerContainer: FrameLayout = itemView.findViewById(R.id.player_container)

        fun setData(data: HomeFeed, holder: AnimeHolder) {
//            holder.nameEng.text = data.animeTitleEn
//            holder.nameJap.text = data.animeTitleJap
//            holder.desc.text = data.animeGenre
//            holder.rating.text = data.animeScore.toString()
//            Glide.with(holder.itemView.context).load(data.animeCoverImage)
//                .centerInside()
//                .into(holder.thumbnail)
            Log.d("HomeRVAdapt", data.animeTrailerLink)
            // if (data.animeTrailerLink.isNotEmpty())
            setupPlayer(holder, data)
        }

        private fun setupPlayer(holder: AnimeHolder, data: HomeFeed) {
            val playerView = YouTubePlayerView(holder.playerContainer.context)

            holder.playerContainer.addView(
                playerView,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            )

            playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    val len = data.animeTrailerLink.length
                    if (len > 12) {
                        Log.d("HomeRVAapt", data.animeTrailerLink)
                        Log.d(
                            "HomeRVAapt",
                            data.animeTrailerLink.subSequence(len - 11, len).toString()
                        )
                        val videoId =
                            data.animeTrailerLink.subSequence(len - 11, len).toString()
                        youTubePlayer.cueVideo(videoId, 0F)
                    }
                }

                override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
                    super.onVideoId(youTubePlayer, videoId)
                }
            })
        }
    }

    class MiscDialog(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val heading: TextView = itemView.findViewById(R.id.tv_miscDiag_heading)
        private val subHeading: TextView = itemView.findViewById(R.id.tv_miscDiag_subhead)
        fun setData(data: HomeFeed, holder: MiscDialog) {
            heading.text = data.miscDialogHeading
            subHeading.text = data.miscDialogSubheading
        }
    }

    class ReminderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            Log.d("ReminderHolder", "created")
        }

        private val headline: ImageView = itemView.findViewById(R.id.iv_image)
        fun setData(data: HomeFeed, holder: ReminderHolder) {
            Glide.with(holder.headline.context)
                .load("https://firebasestorage.googleapis.com/v0/b/firechat-5a222.appspot.com/o/Artboard%201.jpg?alt=media&token=45796976-3aff-4678-92b5-59592fad499f")
                .into(holder.headline)
            holder.itemView.isVisible = false
            holder.itemView.slideUp(headline.context, 500, 250)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("OnCreateViewHolder", "fired")
        if (viewType == reminderType) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_remainder_home, parent, false)
            return ReminderHolder(view)
        }
        if (viewType == miscDialogType) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_misc_dialog, parent, false)
            return MiscDialog(view)
        }
        if (viewType == quotesType) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_quotes_home, parent, false)
            return QuotesHolder(view)
        }
        if (viewType == rickAndMortyType) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_rickandmorty, parent, false)
            return RickMortyHolder(view)
        }
        if (viewType == marvelsType) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_marvel, parent, false)
            return MarvelHolder(view)
        }
        if (viewType == imdbType) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_imdb, parent, false)
            return ImdbHolder(view)
        }
        if (viewType == animeType) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime, parent, false)
            return AnimeHolder(view)
        }
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_jokes, parent, false)
        return JokesHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("OnBindViewHolder", "fired")
        if (holder is QuotesHolder) {
            holder.setData(dataList[position], holder)
        } else if (holder is ReminderHolder) {
            holder.setData(dataList[position], holder)
        } else if (holder is MiscDialog) {
            holder.setData(dataList[position], holder)
        } else if (holder is JokesHolder) {
            holder.setData(dataList[position], holder)
        } else if (holder is RickMortyHolder) {
            holder.setData(dataList[position], holder)
        } else if (holder is MarvelHolder) {
            holder.setData(dataList[position], holder)
            holder.thumbnail.setOnClickListener {
                onItemClickedListener?.onItemClicked(
                    holder.cardView,
                    position,
                    Constants.HomeFeedNaming.MARVEL,
                    dataList[holder.adapterPosition]
                )
            }
        } else if (holder is ImdbHolder) {
            holder.setData(dataList[position], holder)
        } else if (holder is AnimeHolder) {
            holder.setData(dataList[position], holder)
        }
    }

    override fun getItemCount(): Int {
        Log.d("getItemCount", "fired")
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("getItemViewType", "fired")
        return when (dataList[position].dataType) {
            Constants.HomeFeedNaming.MISC_DIALOG -> miscDialogType
            Constants.HomeFeedNaming.QUOTES -> quotesType
            Constants.HomeFeedNaming.JOKES -> jokesType
            Constants.HomeFeedNaming.RICK_MORTY -> rickAndMortyType
            Constants.HomeFeedNaming.MARVEL -> marvelsType
            Constants.HomeFeedNaming.IMDB -> imdbType
            Constants.HomeFeedNaming.ANIME -> animeType
            else -> quotesType
        }
    }

    private var onItemClickedListener: OnItemClickListener? = null
    fun setOnItemClickedListener(listener: OnItemClickListener) {
        onItemClickedListener = listener
    }
}