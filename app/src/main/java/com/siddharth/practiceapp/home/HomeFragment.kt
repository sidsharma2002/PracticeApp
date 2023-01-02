package com.siddharth.practiceapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.adapter.HomeRvAdapter
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.databinding.FragmentHomeBinding
import com.siddharth.practiceapp.ui.activites.MainActivity
import com.siddharth.practiceapp.util.SwipeToDeleteCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeFeedViewMvc: HomeFeedViewMvc
    @Inject lateinit var homeFeedController: HomeFeedController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container == null) {
            return null
        }

        homeFeedViewMvc = HomeFeedViewMvcImpl(inflater, container)
        homeFeedController.bindView(homeFeedViewMvc)
        return homeFeedViewMvc.getRootView()
    }

    override fun onStart() {
        super.onStart()
        homeFeedController.onStart()
    }
}