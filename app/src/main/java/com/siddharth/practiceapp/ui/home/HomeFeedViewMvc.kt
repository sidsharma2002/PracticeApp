package com.siddharth.practiceapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.adapter.HomeRvAdapter
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.databinding.FragmentHomeBinding

interface HomeFeedViewMvc {
    fun bindHomeDataListToView(homeDataList: List<HomeData>)
    fun getRootView(): View?
}

class HomeFeedViewMvcImpl constructor(
    layoutInflater: LayoutInflater,
    container: ViewGroup
) : HomeFeedViewMvc {

    private val binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
    private val adapter: HomeRvAdapter = HomeRvAdapter()

    init {
        binding.rvFragmentsHome.adapter = adapter
    }

    override fun bindHomeDataListToView(homeDataList: List<HomeData>) {
        adapter.submitList(homeDataList)
    }

    override fun getRootView(): View {
        return binding.root
    }
}