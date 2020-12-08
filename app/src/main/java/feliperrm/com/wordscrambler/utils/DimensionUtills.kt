package feliperrm.com.wordscrambler.utils

import android.content.Context

/**
 * Created by FelipeRRM on 4/7/2019.
 */
object DimensionUtills {

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels.
     * @param context Context to get resources and device specific display metrics.
     * @return A float value to represent px equivalent to dp depending on device density.
     */
    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi / 160f)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db.
     * @param context Context to get resources and device specific display metrics.
     * @return A float value to represent dp equivalent to px value.
     */
    fun convertPixelsToDp(px: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return px / (metrics.densityDpi / 160f)
    }

}