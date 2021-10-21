package com.siddharth.practiceapp.customImpl

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.data.entities.HomeData
import android.util.TypedValue




class NewsItemView constructor(
    context: Context,
) : View(context) {

    private lateinit var homeData: HomeData
    private var margin = 8f
    private var paintTitle: Paint = Paint()
    private var paintDesc  = Paint()

    @RequiresApi(Build.VERSION_CODES.O)
    fun populate(homeData: HomeData) {
        Log.d("NewsItemView","populate fired")
        this.homeData = homeData
        setupPaint()
        invalidate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupPaint() {
        paintTitle.color = context.resources.getColor(R.color.white)
        val pixel = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            14F, resources.displayMetrics
        ).toInt()
        paintTitle.textSize = pixel.toFloat()
        paintTitle.typeface = context.resources.getFont(R.font.poppinssemibold)
        paintDesc.color = context.resources.getColor(R.color.white)
        paintDesc.textSize = pixel.toFloat()
        paintDesc.typeface = context.resources.getFont(R.font.poppinsregular)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d("NewsItemView","onDraw fired")
        //canvas?.save()
        homeData.title?.let { canvas?.drawText(it, margin + 12f, margin + 6f, paintTitle) }
        canvas?.drawText(homeData.description,margin + 12f, margin + 6f + paintTitle.textSize, paintTitle)
        //canvas?.restore()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("NewsItemView","onMeasure fired")
        setMeasuredDimension(widthMeasureSpec,200)
    }
}