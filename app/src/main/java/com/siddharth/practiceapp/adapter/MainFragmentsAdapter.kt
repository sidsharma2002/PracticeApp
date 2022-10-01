package com.siddharth.practiceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.siddharth.practiceapp.data.entities.MainDataFrag
import com.siddharth.practiceapp.databinding.ItemBaseFeatureBinding
import com.siddharth.practiceapp.databinding.ItemHeaderFragmentBinding

class MainFragmentsAdapter : ListAdapter<MainDataFrag, MainFragmentsAdapter.AbstractViewHolder>(
    COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<MainDataFrag>(){
            override fun areContentsTheSame(oldItem: MainDataFrag, newItem: MainDataFrag): Boolean {
                return oldItem.toString() == newItem.toString()
            }

            override fun areItemsTheSame(oldItem: MainDataFrag, newItem: MainDataFrag): Boolean {
                return oldItem == newItem
            }
        }
    }
    private val headerType = 2
    private val fragmentType = 1

    private class BasicFeatureViewHolder(private val binding: ItemBaseFeatureBinding) : AbstractViewHolder(binding) {
        override fun bind(item: MainDataFrag) {
            binding.tvBaseFragmentName.text = item.fragmentName
            binding.tvBaseFragmentSubName.text = item.fragmentSubName
        }
    }

    abstract class AbstractViewHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root){
        abstract fun bind(item: MainDataFrag)
    }

    private class HeaderViewHolder(private val binding: ItemHeaderFragmentBinding) : AbstractViewHolder(binding) {
        override fun bind(item: MainDataFrag) {
            binding.chipHeader.text = item.headingName
        }
    }

    override fun onBindViewHolder(holder: AbstractViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        return if (viewType == headerType) {
            HeaderViewHolder(ItemHeaderFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            BasicFeatureViewHolder(ItemBaseFeatureBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val type = getItemViewType(position)
        return if (type == headerType) {
            headerType
        } else {
            fragmentType
        }
    }
}