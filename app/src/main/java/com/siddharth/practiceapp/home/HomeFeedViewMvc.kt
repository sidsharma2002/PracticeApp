package com.siddharth.practiceapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.databinding.FragmentHomeBinding
import com.siddharth.practiceapp.di.compositionRoot.ViewMvcCompositionRoot
import com.siddharth.practiceapp.mvcs.BaseObservableMvcImpl
import com.siddharth.practiceapp.mvcs.ObservableMvc

interface HomeFeedViewMvc : ObservableMvc<HomeFeedViewMvc.Listener> {
    interface Listener {
        fun onHomeItemClicked(homeData: HomeData, position: Int)
    }

    fun bindHomeDataListToView(homeDataList: List<HomeData>)
}

class HomeFeedViewMvcImpl constructor(
    viewMvcCompositionRoot: ViewMvcCompositionRoot,
    binding: FragmentHomeBinding
) : BaseObservableMvcImpl<HomeFeedViewMvc.Listener>(binding.root), HomeFeedViewMvc {

    private val homeItemViewMvcListener = object : HomeItemViewMvc.Listener {
        override fun onHomeItemClicked(homeData: HomeData, position: Int) {
            // delegate to homeFeedViewMvc listeners
            this@HomeFeedViewMvcImpl.getListeners().forEach { listener ->
                listener.onHomeItemClicked(homeData, position)
            }
        }
    }

    private val adapter: HomeRvAdapter =
        HomeRvAdapter(viewMvcCompositionRoot, homeItemViewMvcListener)

    init {
        binding.rvFragmentsHome.adapter = adapter
    }

    override fun bindHomeDataListToView(homeDataList: List<HomeData>) {
        adapter.submitList(homeDataList)
    }
}