package com.siddharth.practiceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.data.entities.HomeData

class HomeRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        @JvmStatic
        val newsType = 1

        @JvmStatic
        val reminderType = 2
    }

    val randomInt = (1..8).random()
    val dataList = ArrayList<HomeData>()

    class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsHeadline: TextView = itemView.findViewById(R.id.tv_newsHeadline)
        val newsDescription: TextView = itemView.findViewById(R.id.tv_newsDesc)
        val newsImage: ImageView = itemView.findViewById(R.id.iv_newsImage)

        fun setData(data: HomeData, holder: NewsHolder, position: Int) {
            holder.newsHeadline.text = data.news!!.title
            holder.newsDescription.text = data.news!!.description
        }
    }

    class ReminderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reminderText: TextView = itemView.findViewById(R.id.tv_reminder)
        fun setData(data: HomeData, holder: ReminderHolder) {
            holder.reminderText.text = "To live a creative life, we must lose our fear of being wrong"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == reminderType) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_remainder_home, parent, false)
            return ReminderHolder(view)
        }
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news_home, parent, false)
        return NewsHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsHolder) {
            holder.setData(dataList[position], holder, position)
        } else if (holder is ReminderHolder) {
            holder.setData(dataList[position], holder)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == randomInt) {
            return reminderType
        }
        return newsType
    }
}