package com.siddharth.practiceapp.customImpl

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.siddharth.computation.splitwise.customDataStructure.HashGapPool
import com.siddharth.practiceapp.R
import java.util.*

/**
 * @author Siddharth Sharma
 */
class CustomRV constructor(
    context: Context,
    attributeSet: AttributeSet
) : RecyclerView(context, attributeSet) {

    private var speedStack: Stack<Int> = Stack()
    private var startTime = System.currentTimeMillis()
    private var endTime: Long = startTime
    private var totalDrag = 0

    private val icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_email_24)
    private val intrinsicWidth = icon!!.intrinsicWidth
    private val intrinsicHeight = icon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = context.resources.getColor(R.color.black_light)
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    private var hashGapPool = HashGapPool<Int, Int>()

    companion object {
        const val TAG = "CustomRV "
    }

    init {
        hashGapPool.addInitialObjectsInPool()
        val result = hashGapPool.pop()
        if (result is HashGapPool.Response.Error) {
            // TODO : Handle Error here
        }
        if (result is HashGapPool.Response.Success) {
            val hashGap = result.data
            hashGap!![1] = 1
            hashGap[2] = 1
        }
    }

    fun setMainPool(newPool: HashGapPool<Int, Int>) {
        hashGapPool = newPool
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        totalDrag += kotlin.math.abs(dy)
    }

    override fun onScrollStateChanged(newState: Int) {
        super.onScrollStateChanged(newState)
        when (newState) {
            SCROLL_STATE_DRAGGING -> {
                startTime = System.currentTimeMillis()
                totalDrag = 0
            }

            SCROLL_STATE_SETTLING -> {
                endTime = System.currentTimeMillis()
                if (startTime != endTime) {
                    val dt = (endTime - startTime).toInt()
                    val speed = totalDrag / dt
                    speedStack.push(speed)
                }
            }
        }
    }

    override fun onDraw(c: Canvas?) {
        super.onDraw(c)

    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
    }
}