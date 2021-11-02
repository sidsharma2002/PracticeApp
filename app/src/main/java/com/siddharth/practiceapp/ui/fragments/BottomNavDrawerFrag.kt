package com.siddharth.practiceapp.ui.fragments

import android.content.res.ColorStateList
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.shape.MaterialShapeDrawable
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.databinding.FragmentBottomNavDrawerBinding
import com.siddharth.practiceapp.ui.navigation.BottomNavigationDrawerCallback
import com.siddharth.practiceapp.util.themeColor

class BottomNavDrawerFrag : Fragment() {
    private lateinit var binding: FragmentBottomNavDrawerBinding
    private val bottomSheetCallback = BottomNavigationDrawerCallback()

    private val behavior: BottomSheetBehavior<FrameLayout> by lazy(LazyThreadSafetyMode.NONE) {
        BottomSheetBehavior.from(binding.backgroundContainer)
    }

    private val backgroundShapeDrawable: MaterialShapeDrawable by lazy(LazyThreadSafetyMode.NONE) {
        val backgroundContext = binding.backgroundContainer.context
        MaterialShapeDrawable(
            backgroundContext,
            null,
            R.attr.bottomSheetStyle,
            0
        ).apply {
            fillColor = ColorStateList.valueOf(
                backgroundContext.themeColor(R.attr.colorOnSecondary)
            )
            elevation = 8f
            initializeElevationOverlay(requireContext())
        }
    }
}