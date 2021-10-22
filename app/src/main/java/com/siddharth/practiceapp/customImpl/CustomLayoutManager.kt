package com.siddharth.practiceapp.customImpl

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.siddharth.practiceapp.R

class CustomLayoutManager constructor(
    resources: Resources,
    private val screenWidth: Int
): RecyclerView.LayoutManager() {

    private var horizontalScrollOffset = 0
    private val viewWidth = resources.getDimensionPixelSize(R.dimen.cardview_compat_inset_shadow)
    // private val recyclerViewHeight = (resources.getDimensionPixelSize(R.dimen.recyclerview_height)).toDouble()

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        recycler?.let {
            detachAndScrapAttachedViews(it)
        }
        if (state?.itemCount!! <= 0) return
        fill(recycler, state)
    }

    private fun fill(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        var top: Int
        var startPosition: Int
        val firstVisiblePosition =
            Math.floor(horizontalScrollOffset.toDouble() / viewWidth.toDouble()).toInt()
        val lastVisiblePosition = (horizontalScrollOffset + screenWidth) / viewWidth

    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        horizontalScrollOffset = dx
        fill(recycler, state)
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

}