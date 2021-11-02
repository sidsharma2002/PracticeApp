package com.siddharth.practiceapp.ui.fragments

import android.content.Intent
import android.graphics.Path
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.siddharth.practiceapp.adapter.OnBoardingAdapter
import com.siddharth.practiceapp.databinding.FragmentOnboarding1Binding
import com.siddharth.practiceapp.databinding.FragmentOnboarding2Binding
import com.siddharth.practiceapp.databinding.FragmentOnboardingBinding
import com.siddharth.practiceapp.ui.activites.MainActivity
import com.siddharth.practiceapp.util.slideUp

class OnBoardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)

        setupUi()
        return binding.root
    }

    private fun setupUi() {
        val onBoardingAdapter =
            OnBoardingAdapter(childFragmentManager, lifecycle)

        onBoardingAdapter.fragList.add(OnBoardingScreen1())
        onBoardingAdapter.fragList.add(OnBoardingScreen2())

        binding.viewPagerOnBoarding.apply {
            adapter = onBoardingAdapter
        }

        binding.viewPagerOnBoarding.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == onBoardingAdapter.fragList.lastIndex &&
                    !binding.btnGetStarted.isVisible
                ) {  // onLastPage
                    binding.btnGetStarted.slideUp(requireContext(), 1500, 100)
                    binding.btnGetStarted.isVisible = true
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnCLickListener()
    }

    private fun setupOnCLickListener() {
        binding.btnGetStarted.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    fun canScrollViewPager(boolean: Boolean) {
        binding.viewPagerOnBoarding.isUserInputEnabled = boolean
    }
}