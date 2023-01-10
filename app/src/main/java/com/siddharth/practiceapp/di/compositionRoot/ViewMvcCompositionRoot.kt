package com.siddharth.practiceapp.di.compositionRoot

import android.view.LayoutInflater
import android.view.ViewGroup
import com.siddharth.practiceapp.databinding.FragmentHomeBinding
import com.siddharth.practiceapp.databinding.ItemNewsHomeBinding
import com.siddharth.practiceapp.home.HomeFeedViewMvc
import com.siddharth.practiceapp.home.HomeFeedViewMvcImpl
import com.siddharth.practiceapp.home.HomeItemViewMvc
import com.siddharth.practiceapp.home.HomeItemViewMvcImpl

class ViewMvcCompositionRoot {
    fun getHomeFeedViewMvc(layoutInflater: LayoutInflater, container: ViewGroup): HomeFeedViewMvc {
        val binding: FragmentHomeBinding =
            FragmentHomeBinding.inflate(layoutInflater, container, false)

        return HomeFeedViewMvcImpl(viewMvcCompositionRoot = this, binding = binding)
    }

    fun getHomeFeedItemViewMvc(parent: ViewGroup): HomeItemViewMvc {
        val binding: ItemNewsHomeBinding = ItemNewsHomeBinding.inflate(
            /* inflater = */ LayoutInflater.from(parent.context),
            /* parent = */ parent,
            /* attachToParent = */ false
        )

        return HomeItemViewMvcImpl(binding)
    }
}