package com.siddharth.practiceapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.databinding.FragmentHomeBinding
import com.siddharth.practiceapp.mvcs.BaseObservableMvcImpl
import com.siddharth.practiceapp.mvcs.ObservableMvc

interface HomeFeedViewMvc : ObservableMvc<HomeFeedViewMvc.Listener> {
    interface Listener {
        fun onHomeItemClicked(homeData: HomeData, position: Int)
    }

    fun bindHomeDataListToView(homeDataList: List<HomeData>)
}

class HomeFeedViewMvcImpl constructor(
    layoutInflater: LayoutInflater,
    container: ViewGroup,
    binding: FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
) : BaseObservableMvcImpl<HomeFeedViewMvc.Listener>(binding.root), HomeFeedViewMvc,
    HomeItemViewMvc.Listener {


    private val adapter: HomeRvAdapter = HomeRvAdapter(homeItemViewMvcListener = this)

    init {
        binding.rvFragmentsHome.adapter = adapter
    }

    override fun bindHomeDataListToView(homeDataList: List<HomeData>) {
        adapter.submitList(homeDataList)
    }

    override fun onHomeItemClicked(homeData: HomeData, position: Int) {
        // delegate to homeFeedViewMvc's listeners
        this@HomeFeedViewMvcImpl.getListeners().forEach { listener ->
            listener.onHomeItemClicked(homeData, position)
        }
    }
}