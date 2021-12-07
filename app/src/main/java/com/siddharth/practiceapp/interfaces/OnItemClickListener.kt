package com.siddharth.practiceapp.interfaces

import android.view.View
import com.siddharth.practiceapp.util.Constants

interface OnItemClickListener {
    fun onItemClicked(view: View, pos: Int, type: String, obj: Any?)
}