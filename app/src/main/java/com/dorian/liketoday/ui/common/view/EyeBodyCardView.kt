package com.dorian.liketoday.ui.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.dorian.liketoday.R
import com.dorian.liketoday.databinding.EyeBodyCardViewBinding

class EyeBodyCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val binding: EyeBodyCardViewBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.eye_body_card_view, this, true)

    init {
        orientation = VERTICAL
    }

}
