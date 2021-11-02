package com.siddharth.practiceapp.util

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils.loadAnimation
import androidx.core.view.isVisible
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.siddharth.practiceapp.R

fun View.slideUp(context: Context, animTime: Long, startOffset: Long) {
    val slideUp = loadAnimation(context, R.anim.slideup).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }
    startAnimation(slideUp)
    isVisible = true
}

fun View.fadeout(context: Context, animTime: Long) {
    val fadeout = loadAnimation(context, R.anim.fadeout).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
    }
    startAnimation(fadeout)
    visibility = View.INVISIBLE
}