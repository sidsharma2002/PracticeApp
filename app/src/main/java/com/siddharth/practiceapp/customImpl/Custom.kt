package com.siddharth.practiceapp.customImpl

import android.content.Context
import android.widget.EdgeEffect
import androidx.recyclerview.widget.RecyclerView

class CustomEdgeEffectFactory : RecyclerView.EdgeEffectFactory() {

    override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
        return  CustomEdgeEffect(view.context)
    }
}

class CustomEdgeEffect(context: Context) : EdgeEffect(context) {
    override fun onPull(deltaDistance: Float, displacement: Float) {
        super.onPull(deltaDistance/2, displacement)
    }

    override fun onPullDistance(deltaDistance: Float, displacement: Float): Float {
        return super.onPullDistance(deltaDistance/2, displacement)
    }

    override fun onRelease() {
        super.onRelease()
    }
}