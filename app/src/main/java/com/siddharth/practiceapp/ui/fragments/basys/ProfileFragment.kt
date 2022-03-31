package com.siddharth.practiceapp.ui.fragments.basys

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.databinding.FragmentDashboardBinding
import com.siddharth.practiceapp.databinding.FragmentProfileBinding
import com.siddharth.practiceapp.util.slideUp
import kotlinx.coroutines.delay

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setupUi()
        return binding.root
    }

    private fun setupUi() {
        lifecycleScope.launchWhenResumed {
            binding.tv1.slideUp(requireContext(), 1000, 200)
            delay(1000)
            binding.tv2.slideUp(requireContext(), 1000, 200)
        }

        binding.tvUsername.visibility = View.INVISIBLE
    }
}