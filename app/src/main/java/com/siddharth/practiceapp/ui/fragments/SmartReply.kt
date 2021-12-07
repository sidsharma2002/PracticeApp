package com.siddharth.practiceapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.siddharth.practiceapp.databinding.FragmentSmartreplyBinding
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.viewModels.SmartReplyViewModel

class SmartReplyFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSmartreplyBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: SmartReplyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSmartreplyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setupClickListener()
        subscribeToObservers()
    }

    private fun setupUi() {

    }

    private fun setupClickListener() {
        binding.btnSearch.setOnClickListener {
            viewmodel.getSmartReply(binding.myReply.text.toString())
        }
    }

    private fun subscribeToObservers() {
        viewmodel.smartReply.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    for (i in it.data!!)
                        binding.roboticReply.text = i + "\n"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}