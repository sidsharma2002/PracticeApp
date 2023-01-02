package com.siddharth.practiceapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.databinding.ItemNewsHomeBinding

class HomeRvAdapter constructor(
    private val controller: HomeAdapterController? = null,
    private val homeItemViewMvcListener: HomeItemViewMvc.Listener
) : ListAdapter<HomeData, ViewHolder>(COMPARATOR) {

    class NewsHolder(private val homeItemViewMvc: HomeItemViewMvc) :
        ViewHolder(homeItemViewMvc.getRootView()) {

        fun bind(item: HomeData, position: Int) {
            homeItemViewMvc.bindHomeItemToView(item, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val homeItemViewMvc: HomeItemViewMvc = HomeItemViewMvcImpl(parent)
        homeItemViewMvc.registerListener(homeItemViewMvcListener)
        return NewsHolder(homeItemViewMvc)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is NewsHolder -> holder.bind(getItem(position), position)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<HomeData>() {
            override fun areContentsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
                return oldItem == newItem
            }
        }
    }
}