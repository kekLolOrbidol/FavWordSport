package feliperrm.com.wordscrambler.utils

import android.graphics.Color
import android.view.View
import androidx.databinding.BindingAdapter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import feliperrm.com.wordscrambler.R

/**
 * Sets up a Pie Chart using a list of PieEntry through Data Binding
 */
@BindingAdapter("pieEntries")
fun PieChart.setData(pieEntries: List<PieEntry>?) {
    pieEntries?.let {
        val dataSet = PieDataSet(it, "").apply {
            setColors(
                context.resources.getColor(R.color.colorPrimary),
                context.resources.getColor(R.color.colorAccent)
            )
            valueTextSize = 24f
            valueTextColor = Color.WHITE
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
        }
        val data = PieData(dataSet)
        setData(data)
        animateXY(400, 400, Easing.EaseInOutSine)
    }
}

/**
 * Sets the shrink animation on touch using data binding.
 * The parameters are optional and will fallback to the default values if not set.
 * The target view is the same touch view unless explicitly defined.
 */
@BindingAdapter("shrinkTouchTarget", "shrinkTouchEndSize", "shrinkTouchDuration", requireAll = false)
fun View.setShrinkTouchAnim(targetView: View?, endSize: Float?, animDuration: Long?) {
    this.setOnTouchListener(
        AnimationUtills.getShrinkAnimation(
            targetView
                ?: this, endSize ?: 0.95f, animDuration
                ?: 250
        )
    )
}