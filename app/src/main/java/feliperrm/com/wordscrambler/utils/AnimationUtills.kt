package feliperrm.com.wordscrambler.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

/**
 * Created by FelipeRRM on 4/7/2019.
 */
object AnimationUtills {

    /**
     * This method creates an OnTouchListener that animates a view's Z position (elevation) when a view is pressed.
     * It always returns false, meaning that the touch event will not be consumed and therefore allowing other logic to be implemented using it.
     *
     * @param viewToAnimate    view that will have it's Z position changed.
     * @param initialElevation original (not pressed) elevation of the view.
     * @param endElevation     end (when pressed) elevation of the view.
     * @param duration         time in, in miliseconds, that it takes from the view to change between initialElevation to endElevation.
     * @param isDp             wheather the values provided are in DP (true) or in pixels (false)
     */
    @SuppressLint("ClickableViewAccessibility")
    @JvmStatic
    fun getAnimationElevation(
        viewToAnimate: View,
        initialElevation: Float,
        endElevation: Float,
        duration: Long,
        isDp: Boolean
    ): View.OnTouchListener? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val startElevationPixels = if (isDp) DimensionUtills.convertDpToPixel(
                initialElevation,
                viewToAnimate.context
            ) else initialElevation
            val endElevationPixels =
                if (isDp) DimensionUtills.convertDpToPixel(endElevation, viewToAnimate.context) else endElevation
            viewToAnimate.elevation = 0f
            viewToAnimate.translationZ = startElevationPixels
            return View.OnTouchListener { view, motionEvent ->
                val action = motionEvent.actionMasked
                when (action) {
                    MotionEvent.ACTION_DOWN -> animateZ(viewToAnimate, endElevationPixels, duration)
                    MotionEvent.ACTION_UP -> animateZ(viewToAnimate, startElevationPixels, duration)
                    MotionEvent.ACTION_CANCEL -> animateZ(viewToAnimate, startElevationPixels, duration)
                }
                return@OnTouchListener false
            }

        }
        return null
    }

    /**
     * This method creates an OnTouchListener that animates a view's size when a view is pressed.
     * It always returns false, meaning that the touch event will not be consumed and therefore allowing other logic to be implemented using it.
     *
     * @param viewToAnimate view that will have it's size changed.
     * @param endSize       end (when pressed) size of the view.
     * @param duration      time in, in miliseconds, that it takes from the view to change between initialElevation to endElevation.
     * @retuns the OnTouchListener
     */
    @SuppressLint("ClickableViewAccessibility")
    @JvmStatic
    fun getShrinkAnimation(viewToAnimate: View, endSize: Float, duration: Long): View.OnTouchListener {
        val startSize = viewToAnimate.scaleX
        return View.OnTouchListener { v, event ->
            val action = event.actionMasked
            when (action) {
                MotionEvent.ACTION_DOWN -> animateScaleXY(viewToAnimate, endSize, duration)
                MotionEvent.ACTION_UP -> animateScaleXY(viewToAnimate, startSize, duration)
                MotionEvent.ACTION_CANCEL -> animateScaleXY(viewToAnimate, startSize, duration)
                else -> {
                }
            }
            return@OnTouchListener false
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateZ(v: View, elevation: Float, duration: Long): ViewPropertyAnimator {
        return v.animate().translationZ(elevation).setDuration(duration).setStartDelay(0)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun animateScaleXY(v: View, scale: Float, duration: Long): ViewPropertyAnimator {
        return v.animate().scaleX(scale).scaleY(scale).setDuration(duration)
            .setInterpolator(FastOutSlowInInterpolator()).setStartDelay(0)
    }

}