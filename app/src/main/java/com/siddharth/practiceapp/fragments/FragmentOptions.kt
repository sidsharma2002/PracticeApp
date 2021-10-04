package com.siddharth.practiceapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.databinding.FragmentFragABinding
import com.siddharth.practiceapp.databinding.FragmentOptionsBinding


class FragmentOptions : Fragment() {

    private var _binding: FragmentOptionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        setupUi()
        return binding.root
    }

    private fun setupUi() {

    }

}