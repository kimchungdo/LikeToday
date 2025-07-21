package com.dorian.liketoday.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dorian.liketoday.data.WeightEntry
import com.dorian.liketoday.databinding.FragmentWeightChartBinding
import com.dorian.liketoday.viewmodel.WeightViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class WeightChartFragment : Fragment() {
    private var _binding: FragmentWeightChartBinding? = null
    private val binding get() = _binding!!

    private val weightViewModel: WeightViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeightChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        weightViewModel.getAllEntries().observe(viewLifecycleOwner) { entries ->
            updateChart(entries)
        }
    }

    private fun updateChart(entries: List<WeightEntry>) {
        val morningEntries = mutableListOf<Entry>()
        val eveningEntries = mutableListOf<Entry>()
        val labels = mutableListOf<String>()

        val sorted = entries.sortedBy { it.date + it.type }

        val dateIndexMap = mutableMapOf<String, Int>()
        var index = 0

        for (entry in sorted) {
            val key = entry.date
            if (!dateIndexMap.containsKey(key)) {
                dateIndexMap[key] = index++
                labels.add(key)
            }
            val x = dateIndexMap[key]?.toFloat() ?: continue
            val y = entry.weight
            val target = if (entry.type == "morning") morningEntries else eveningEntries
            target.add(Entry(x, y))
        }

        val morningDataSet = LineDataSet(morningEntries, "아침 몸무게").apply{
            color = Color.BLUE
            circleRadius = 3f
            setCircleColor(Color.BLUE)
        }

        val eveningDataSet = LineDataSet(eveningEntries, "저녁 몸무게").apply{
            color = Color.GREEN
            circleRadius = 3f
            setCircleColor(Color.GREEN)
        }

        binding.lineChart.apply {
            data = LineData(morningDataSet, eveningDataSet)
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            description.isEnabled = false
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}