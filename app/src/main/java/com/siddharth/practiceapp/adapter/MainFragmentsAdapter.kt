package com.siddharth.practiceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.data.entities.MainDataFrag

class MainFragmentsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val dataList = ArrayList<MainDataFrag>()
    private val headerType = 2
    private val fragmentType = 1

    class BasicFeatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fragName: TextView = itemView.findViewById(R.id.tv_baseFragment_name)
        val fragSubName: TextView = itemView.findViewById(R.id.tv_baseFragment_subName)
        fun setData(dataFrag: MainDataFrag, holder: BasicFeatureViewHolder) {
            holder.fragName.text = dataFrag.fragmentName
            holder.fragSubName.text = dataFrag.fragmentSubName
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headingName: Chip = itemView.findViewById(R.id.chip_header)
        fun setData(dataFrag: MainDataFrag, holder: HeaderViewHolder) {
            holder.headingName.text = dataFrag.headingName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType == headerType) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_header_fragment, parent, false)
            HeaderViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_base_feature, parent, false)
            BasicFeatureViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BasicFeatureViewHolder) {
            holder.setData(dataList[position], holder)
        } else {
            (holder as HeaderViewHolder).setData(dataList[position], holder)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val type = dataList[position].viewType
        return if (type == headerType) {
            headerType
        } else {
            fragmentType
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}