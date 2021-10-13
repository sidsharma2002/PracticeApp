package com.siddharth.computation.splitwise

import android.content.Context
import android.view.Surface
import android.view.SurfaceView
import kotlin.collections.ArrayList

class Sample constructor(private val context: Context) {

    private val list: ArrayList<SurfaceView> = ArrayList()
    private val db: HashMap<Int, SurfaceView> = HashMap()

    private fun doWork() {
        list.add(SurfaceView(context))
        db[1] = SurfaceView(context)
    }
}