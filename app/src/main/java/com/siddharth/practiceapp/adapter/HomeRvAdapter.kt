package com.siddharth.practiceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.databinding.ItemNewsHomeBinding
import com.siddharth.practiceapp.databinding.ItemRemainderHomeBinding
import com.siddharth.practiceapp.util.slideUp

class HomeRvAdapter : ListAdapter<HomeData, ViewHolder>(COMPARATOR) {

    companion object {
        @JvmStatic
        val newsType = 1

        private val COMPARATOR = object : DiffUtil.ItemCallback<HomeData>() {
            override fun areContentsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
                return oldItem == newItem
            }
        }
    }

    class NewsHolder(private val binding: ItemNewsHomeBinding) : ViewHolder(binding.root) {
        fun bind(item: HomeData) {
            binding.tvNewsHeadline.text = item.title
            binding.tvNewsDesc.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsHolder(
            ItemNewsHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder as NewsHolder
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return newsType
    }
}