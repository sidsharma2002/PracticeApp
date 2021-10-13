package com.siddharth.practiceapp.customViews

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class CustomRV(
    context: Context
) : RecyclerView(context) {

    companion object {
        val TAG = "CustomRV "
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        calculateDistance(dx, dy)
    }

    private fun calculateDistance(dx: Int, dy: Int) {
        Log.d(TAG, "moved x : $dx moved y : $dy")
    }
}