package com.dorian.liketoday.ui.common.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.dorian.liketoday.R
import com.dorian.liketoday.data.AppDatabase
import com.dorian.liketoday.databinding.WeightCardViewBinding
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs
import androidx.core.graphics.toColorInt

class WeightCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val binding: WeightCardViewBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.weight_card_view, this, true)

    private val lifecycleOwner = context as? LifecycleOwner
        ?: throw IllegalStateException("Context must implement LifecycleOwner")

    private val weightDao = AppDatabase.getDatabase(context).weightDao()

    var onInputClicked: (() -> Unit)? = null

    var onCardClicked: (() -> Unit)? = null

    init {
        orientation = VERTICAL

        binding.buttonWeightInput.setOnClickListener {
            onInputClicked?.invoke()
        }

        binding.cardLayout.setOnClickListener {
            onCardClicked?.invoke()
        }

        loadWeightData()
    }

    private fun loadWeightData() {
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)
        val formatter = DateTimeFormatter.ISO_DATE
        val todayStr = today.format(formatter)
        val yestStr = yesterday.format(formatter)

        lifecycleOwner.lifecycleScope.launch {
            val todayEntries = weightDao.getEntriesByDateDirect(todayStr)
            val yestEntries = weightDao.getEntriesByDateDirect(yestStr)

            val morning = todayEntries.find { it.type == "morning" }?.weight
            val night = yestEntries.find { it.type == "evening" }?.weight

            if (morning != null) {
                binding.textMorningWeight.text = "%.2f kg".format(morning)
                binding.imageSun.apply{
                    setImageResource(R.drawable.ic_sun)
                    setColorFilter("#FCB64C".toColorInt())
                }
            }

            if (night != null) {
                binding.textNightWeight.text = "%.2f kg".format(night)
                binding.imageMoon.apply{
                    setImageResource(R.drawable.ic_moon)
                    setColorFilter("#837AE5".toColorInt())
                }
            }

            if (morning != null && night != null) {
                val diff = morning - night
                val sign = when {
                    diff < 0 -> "⬇️"
                    diff > 0 -> "⬆️"
                    else -> "➖"
                }

                val diffColor = if (diff < 0) "#388E3C" else if (diff > 0) "#D32F2F" else "#666666"
                binding.textWeightDiff.text = "$sign %.0fg".format(abs(diff * 1000))
                binding.textWeightDiff.setTextColor(Color.parseColor(diffColor))

                // 간단한 인사이트 예시
                binding.textWeightInsight.text = when {
                    diff < 0 -> "어제보다 가벼워졌어요! 수분이 빠졌을 수도 있어요."
                    diff > 0 -> "밤새 몸무게가 늘었어요. 식사나 수분 영향일 수 있어요."
                    else -> "변화가 없네요. 일정한 루틴을 유지 중이에요!"
                }
            }
        }
    }
}
