package com.siddharth.practiceapp.customImpl

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ProgressBar
import com.google.android.material.button.MaterialButton

class LoadingButton constructor(
    context: Context,
    attributeSet: AttributeSet
) : MaterialButton(context, attributeSet) {

    private val paint : Paint = Paint()
    private var loading : Boolean = false
    private val progressBar = ProgressBar(context)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    fun startLoading() {
            loading = true
            invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }
}