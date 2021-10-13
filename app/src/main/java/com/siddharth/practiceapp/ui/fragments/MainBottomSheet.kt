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
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.databinding.FragmentBottomsheetHomeBinding
import com.siddharth.practiceapp.databinding.FragmentFragABinding


class MainBottomSheet : BottomSheetDialogFragment() {
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var _binding: FragmentBottomsheetHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomsheetHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.fragment_bottomsheet_home, null)
        dialog.setContentView(view)
        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        setupUi()
        return dialog
    }

    private fun setupUi() {
            binding.rvFragmentsHome.apply {

            }
    }

    override fun onStart() {
        super.onStart()
        bottomSheetBehavior!!.isHideable = true
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior!!.peekHeight = 700
    }
}