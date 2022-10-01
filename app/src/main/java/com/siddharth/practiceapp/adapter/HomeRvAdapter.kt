package com.siddharth.practiceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.databinding.ItemNewsHomeBinding
import com.siddharth.practiceapp.databinding.ItemRemainderHomeBinding
import com.siddharth.practiceapp.util.slideUp

class HomeRvAdapter : ListAdapter<HomeData, HomeRvAdapter.MyAbstractViewHolder>(COMPARATOR) {

    companion object {
        @JvmStatic
        val newsType = 1

        @JvmStatic
        val reminderType = 2

        private val COMPARATOR = object : DiffUtil.ItemCallback<HomeData>(){
            override fun areContentsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var positionForSpeedItem = -1
    private var shouldShouldSpeedItem = false

    class NewsHolder(private val binding: ItemNewsHomeBinding) : MyAbstractViewHolder(binding) {
        override fun bind(item: HomeData) {
            binding.tvNewsHeadline.text = item.title
            binding.tvNewsDesc.text = item.description
        }
    }

    abstract class MyAbstractViewHolder(binding: ViewBinding): ViewHolder(binding.root){
        abstract fun bind(item:HomeData)
    }

    class ReminderHolder(private val binding: ItemRemainderHomeBinding) : MyAbstractViewHolder(binding) {
        override fun bind(item: HomeData) {
            Glide.with(binding.ivImage.context)
                .load("https://firebasestorage.googleapis.com/v0/b/firechat-5a222.appspot.com/o/Artboard%201.jpg?alt=media&token=45796976-3aff-4678-92b5-59592fad499f")
                .into(binding.ivImage)
            binding.root.isVisible = false
            binding.root.slideUp(binding.root.context, 500, 250)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAbstractViewHolder {
        return if (viewType == reminderType){
            ReminderHolder(ItemRemainderHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            NewsHolder(ItemNewsHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }


    override fun onBindViewHolder(holder: MyAbstractViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 10) {
            return reminderType
        }
        return newsType
    }
}