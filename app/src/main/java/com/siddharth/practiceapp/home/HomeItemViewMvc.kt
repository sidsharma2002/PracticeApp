package com.siddharth.practiceapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.databinding.ItemNewsHomeBinding
import com.siddharth.practiceapp.home.HomeItemViewMvc.*
import com.siddharth.practiceapp.mvcs.BaseObservableMvcImpl
import com.siddharth.practiceapp.mvcs.ObservableMvc
import java.util.LinkedList

interface HomeItemViewMvc: ObservableMvc<Listener> {
    interface Listener {
        fun onHomeItemClicked(homeData: HomeData, position: Int)
    }
    fun bindHomeItemToView(homeData: HomeData, position: Int)
}

class HomeItemViewMvcImpl constructor(
    private val binding: ItemNewsHomeBinding
) : BaseObservableMvcImpl<Listener>(binding.root), HomeItemViewMvc {

    override fun bindHomeItemToView(homeData: HomeData, position: Int) {
        binding.tvNewsHeadline.text = homeData.title
        binding.tvNewsDesc.text = homeData.description

        binding.root.setOnClickListener {
            for (listener in getListeners()) {
                listener.onHomeItemClicked(homeData, position)
            }
        }
    }
}