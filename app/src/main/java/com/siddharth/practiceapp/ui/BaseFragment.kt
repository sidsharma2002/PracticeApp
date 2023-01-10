package com.siddharth.practiceapp.ui

import androidx.fragment.app.Fragment
import com.siddharth.practiceapp.di.compositionRoot.ViewMvcCompositionRoot

/**
 * NOTE : Keep code in this class to a minimum.
 */
open class BaseFragment : Fragment() {
    val viewMvcCompositionRoot = ViewMvcCompositionRoot()
}