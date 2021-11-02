package com.siddharth.practiceapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.siddharth.practiceapp.adapter.OnBoardingAdapter
import com.siddharth.practiceapp.databinding.FragmentOnboarding1Binding
import com.siddharth.practiceapp.databinding.FragmentOnboardingBinding
import com.siddharth.practiceapp.util.fadeout
import com.siddharth.practiceapp.util.slideUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnBoardingScreen1 : Fragment() {

    private var _binding: FragmentOnboarding1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboarding1Binding.inflate(inflater, container, false)

        performAnim()
        return binding.root
    }

    private fun performAnim() {
        lifecycleScope.launchWhenResumed {
            (parentFragment as OnBoardingFragment).canScrollViewPager(false)
            binding.tvOnBoard1Heading.slideUp(requireContext(), 1000, 25)
            binding.tvOnBoard1Subhead.isVisible = false
            delay(1000)
            binding.tvOnBoard1Subhead.slideUp(requireContext(), 1000, 25)
            delay(1500)
            binding.tvOnBoard1Heading.fadeout(requireContext(),1000)
            delay(3000)
            binding.tvOnBoard1Heading.apply {
                text = "Swipe Right"
               // setTextColor(Color.GRAY)
            }

            binding.tvOnBoard1Heading.slideUp(requireContext(), 1000, 0)
            (parentFragment as OnBoardingFragment).canScrollViewPager(true)
        }
    }
}