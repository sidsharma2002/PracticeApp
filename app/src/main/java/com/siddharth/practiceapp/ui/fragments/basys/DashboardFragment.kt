package com.siddharth.practiceapp.ui.fragments.basys

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.databinding.FragmentDashboardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        setData(4, 10f)
        binding.pieChart.apply {
            this.animateY(1400, Easing.EaseInOutQuad);
        }
    }

    private fun setData(count: Int, range: Float) {
        val entries: ArrayList<PieEntry> = ArrayList()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            entries.add(
                PieEntry(
                    (Math.random() * range + range / 5).toFloat(),
                    getDisease(i),
                    resources.getDrawable(R.drawable.ic_arrow_drop_up)
                )
            )
        }
        val dataSet = PieDataSet(entries, "Election Results")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors
        val colors: ArrayList<Int> = ArrayList()
        for (c in MATERIAL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(21f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.DKGRAY)
        binding.pieChart.setEntryLabelColor(Color.BLACK)
        binding.pieChart.setData(data)
        binding.pieChart.setBackgroundColor(Color.TRANSPARENT)
        binding.pieChart.setHoleColor(Color.TRANSPARENT)
        binding.pieChart.setTransparentCircleColor(Color.TRANSPARENT)
        // undo all highlights
        binding.pieChart.highlightValues(null)
        binding.pieChart.invalidate()
    }

    private fun getDisease(i: Int): String {
        if (i == 0) return "Fever"
        if (i == 1) return "Viral"
        if (i == 2) return "Covid-19"
        if (i == 3) return "High BP"
        return ""
    }

    val MATERIAL_COLORS = intArrayOf(
        rgb("#FF8C9EFF"), rgb("FFFF80AB"), rgb("#FFF9FD80"), rgb("#3498db")
    )

    /**
     * Converts the given hex-color-string to rgb.
     *
     * @param hex
     * @return
     */
    private fun rgb(hex: String): Int {
        val color = hex.replace("#", "").toLong(16).toInt()
        val r = color shr 16 and 0xFF
        val g = color shr 8 and 0xFF
        val b = color shr 0 and 0xFF
        return Color.rgb(r, g, b)
    }
}

