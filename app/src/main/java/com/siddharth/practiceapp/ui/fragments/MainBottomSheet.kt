package com.siddharth.practiceapp.ui.fragments

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.os.Bundle
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.adapter.MainFragmentsAdapter
import com.siddharth.practiceapp.data.entities.MainDataFrag
import com.siddharth.practiceapp.databinding.FragmentBottomsheetHomeBinding
import com.siddharth.practiceapp.databinding.FragmentFragABinding


class MainBottomSheet : BottomSheetDialogFragment() {
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var _binding: FragmentBottomsheetHomeBinding? = null
    private val binding get() = _binding!!
    private val mainAdapter: MainFragmentsAdapter by lazy { MainFragmentsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentBottomsheetHomeBinding.inflate(inflater,container,false)
        val root: View = binding.root
        setupUi()
        addDataToAdapter()
        return root
    }
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
//        val view = View.inflate(context, R.layout.fragment_bottomsheet_home, null)
//        dialog.setContentView(view)
//
//        return dialog
//    }

    private fun setupUi() =
        binding.rvFragmentsHome.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
        }

    private fun addDataToAdapter() {
        val itemList = listOf(
            MainDataFrag("", "Basic Features", "", 2),
            MainDataFrag("Home", "", "basic", 1),
            MainDataFrag("TODO List", "", "basic",1),
            MainDataFrag("", "Pro Features", "", 2),
            MainDataFrag("PDF Reader", "", "basic", 1),
            MainDataFrag("Workout Reminder", "", "basic",1),
            MainDataFrag("Calories Calculator", "", "basic", 1),
            MainDataFrag("Yoga Teacher", "", "basic",1)
        )
        mainAdapter.submitList(itemList)
        mainAdapter.notifyItemRangeChanged(0,8)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}