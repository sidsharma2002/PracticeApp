package com.siddharth.practiceapp.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.util.slideUp

class HomeRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        @JvmStatic
        val newsType = 1

        @JvmStatic
        val reminderType = 2
    }

    private var positionForSpeedItem = -1
    private var shouldShouldSpeedItem = false
    val dataList = ArrayList<HomeData>()

    class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            Log.d("NewsHolder", "created")
        }
       private val newsHeadline: TextView = itemView.findViewById(R.id.tv_newsHeadline)
       private val newsDescription: TextView = itemView.findViewById(R.id.tv_newsDesc)
       // private val newsView = itemView as NewsItemView

        @RequiresApi(Build.VERSION_CODES.O)
        fun setData(data: HomeData, holder: NewsHolder, position: Int) {
         holder.newsHeadline.text = data.title
         holder.newsDescription.text = data.description
         // newsView.populate(data)
        }
    }

    class ReminderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            Log.d("ReminderHolder", "created")
        }
        private val headline : TextView = itemView.findViewById(R.id.tv_headline)
        fun setData(data: HomeData, holder: ReminderHolder) {
            // holder.reminderText.text = textToSet
            headline.slideUp(headline.context,800,250)
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
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news_home, parent, false)
            // NewsItemView(parent.context)
        return NewsHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("OnBindViewHolder", "fired")
        if (holder is NewsHolder) {
            holder.setData(dataList[position],holder,position)
        } else if (holder is ReminderHolder) {
            holder.setData(dataList[position], holder)
        }
    }

    override fun getItemCount(): Int {
        Log.d("getItemCount", "fired")
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("getItemViewType", "fired")
        if (position == 10) {
            return reminderType
        }
        return newsType
    }
}