package com.siddharth.practiceapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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
    private lateinit var adapter: HomeRvAdapter
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
        setupListeners()
        subscribeToObservers()
    }

    private fun setupUi() {


        adapter = HomeRvAdapter()
        binding.rvFragmentsHome.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = LinearLayoutManager(context)
            val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    handleItemSwipe(viewHolder, direction)
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun handleItemSwipe(viewHolder: ViewHolder, direction: Int) {
        adapter.dataList.removeAt(viewHolder.adapterPosition)
        adapter.notifyItemRemoved(viewHolder.adapterPosition)
    }


    private fun setupListeners() {
        // TODO : to implement
    }

    private fun subscribeToObservers() {

        viewmodel.homeDataList.observe(viewLifecycleOwner) {
            if (it is Response.Success) {
                (requireActivity() as MainActivity).hideSideBar()
                Log.d(TAG, "size of homeDataList from db is ${it.data!!.size}")
                adapter.dataList.clear()
                adapter.dataList.addAll(it.data)
                adapter.notifyItemRangeChanged(0, it.data.size)
            }
            if (it is Response.Loading) {
                (requireActivity() as MainActivity).showSideBar()
                adapter.dataList.clear()
                it.data?.let { it1 ->
                    adapter.dataList.addAll(it1)
                    adapter.notifyItemRangeChanged(0, it1.size)
                }
            }
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