package com.siddharth.practiceapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.adapter.HomeRvAdapter
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.databinding.FragmentHomeBinding
import com.siddharth.practiceapp.ui.activites.MainActivity
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.util.SwipeToDeleteCallback
import com.siddharth.practiceapp.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val TAG = this.javaClass.simpleName
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter: HomeRvAdapter by lazy { HomeRvAdapter() }
    private val viewmodel: HomeViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        printLifeCycleState("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        printLifeCycleState("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        printLifeCycleState("onCreateView")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        printLifeCycleState("onViewCreated")
        printViewLifeCycleState()

        setupUi()
        subscribeToObservers()
    }

    private fun setupUi() {
        binding.rvFragmentsHome.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun subscribeToObservers() {
        viewmodel.homeDataListLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    TODO()
                }

                is Response.Loading -> {
                    TODO()
                }

                is Response.Error -> {
                    TODO()
                }
            }
        }
    }

    private fun hideProgressBarAndSubmitListToAdapter(homeDataList : List<HomeData>) {
        hideProgressBarAndSubmitListToAdapter(homeDataList)
        (requireActivity() as MainActivity).hideSideBar()
        adapter.submitList(homeDataList)
    }

    private fun showProgressBarAndSubmitListToAdapter(homeDataList: List<HomeData>?) {
        (requireActivity() as MainActivity).showSideBar()
        homeDataList?.let { it1 ->
            adapter.submitList(it1)
        }
    }

    override fun onStart() {
        super.onStart()
        printLifeCycleState("onStart")
    }

    override fun onResume() {
        super.onResume()
        printLifeCycleState("onResume")
    }

    override fun onPause() {
        super.onPause()
        printLifeCycleState("onPause")
    }

    override fun onStop() {
        super.onStop()
        printLifeCycleState("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        printLifeCycleState("onDestroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        printLifeCycleState("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        printLifeCycleState("onDetach")
    }

    private fun printViewLifeCycleState() {
        println("view lifecycle owner : " + viewLifecycleOwner.lifecycle.currentState.name)
    }

    private fun printLifeCycleState(callbackName: String) {
        println("Fragment B lifecycle state is : $callbackName +  " + lifecycle.currentState.name)
    }
}