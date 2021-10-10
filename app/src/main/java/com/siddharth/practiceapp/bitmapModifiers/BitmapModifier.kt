package com.siddharth.practiceapp.bitmapModifiers

import android.graphics.Bitmap
import android.graphics.Color

/**
 * @author Siddharth sharma
 */
object BitmapModifier {
    private var newRed: Int = 0;
    private var newGreen: Int = 0;
    private var newBlue: Int = 0
    const val GREY_FILTER = "grey_filter"

    fun applyFilter(bitmap: Bitmap, filter: String): Bitmap {
        val imageHeight = bitmap.height
        val imageWidth = bitmap.width

        for (i in 0 until imageWidth) {
            for (j in 0 until imageHeight) {
                val oldPixel: Int = bitmap.getPixel(i, j)
                val oldRed: Int = Color.red(oldPixel)
                val oldBlue: Int = Color.blue(oldPixel)
                val oldGreen: Int = Color.green(oldPixel)
                val oldAlpha: Int = Color.alpha(oldPixel)

                runAlgorithm(oldRed, oldGreen, oldBlue, oldAlpha, filter)

                val newPixel: Int = Color.argb(oldAlpha, newRed, newGreen, newBlue)
                bitmap.setPixel(i, j, newPixel)
            }
        }
        return bitmap
    }

    private fun runAlgorithm(oldRed: Int, oldGreen: Int, oldBlue: Int, oldAlpha: Int, filter: String) {
        when(filter){
            GREY_FILTER -> applyGreyFilter(oldRed, oldGreen, oldBlue, oldAlpha)
        }
    }

    private fun applyGreyFilter(oldRed: Int, oldGreen: Int, oldBlue: Int, oldAlpha: Int) {
        val intensity = (oldRed + oldBlue + oldGreen) / 3
        newRed = intensity
        newBlue = intensity
        newGreen = intensity
    }
}