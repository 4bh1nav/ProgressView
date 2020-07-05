package com.abhinav.progress_view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Interpolator
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes


class ProgressView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {


    /** background color of the ProgressView. */
    @ColorInt
    private var colorBackground: Int = getColorFromResources(R.color.def_background_color)

    /**
     * Interpolator used for state change animations.
     */
    private var progressAnimation: ProgressViewAnimation = ProgressViewAnimation.NORMAL

    /**
     * an orientation of the [ProgressView].
     * [ProgressViewOrientation.HORIZONTAL], [ProgressViewOrientation.VERTICAL]
     * the default orientation is [ProgressViewOrientation.HORIZONTAL].
     * */
    private var orientation = ProgressViewOrientation.HORIZONTAL

    /** duration of the progress animation. */
    private var animationDuration: Long = 650L

    /**
     * If true, view will animate changes when new data is submitted.
     * If false, state change will happen instantly.
     */
    private var autoAnimate: Boolean = true

    /** corner radius of the [ProgressView]'s container. */
    @Px
    private var radius: Float = context.dpToPx(20f)


    init {
        context.withStyledAttributes(attrs,R.styleable.ProgressView){
            colorBackground = getColor(R.styleable.ProgressView_colorBackground,getColorFromResources(R.color.def_background_color))

            when (getInt(
                    R.styleable.ProgressView_progressView_orientation,
                    ProgressViewOrientation.HORIZONTAL.value)) {
                0 -> orientation = ProgressViewOrientation.HORIZONTAL
                1 -> orientation = ProgressViewOrientation.VERTICAL
            }

            when (getInt(R.styleable.ProgressView_progressView_animation, ProgressViewAnimation.NORMAL.value)) {
                ProgressViewAnimation.NORMAL.value -> progressAnimation = ProgressViewAnimation.NORMAL
                ProgressViewAnimation.BOUNCE.value -> progressAnimation = ProgressViewAnimation.BOUNCE
                ProgressViewAnimation.DECELERATE.value -> progressAnimation = ProgressViewAnimation.DECELERATE
                ProgressViewAnimation.ACCELERATEDECELERATE.value -> progressAnimation = ProgressViewAnimation.ACCELERATEDECELERATE
            }

            autoAnimate = getBoolean(R.styleable.ProgressView_progressView_autoAnimate,true)
        }
    }

    private var progressData : ProgressData? = null

    private var bgRect = RectF(0f,0f,0f,0f)
    private var rect = RectF(0f,0f,0f,0f)
    private var progressWidth: Float = 0f
    private var progressHeight: Float = 0f

    private val bgPaint: Paint by lazy {
        Paint().apply {
        strokeCap = Paint.Cap.ROUND
        color = colorBackground
        isAntiAlias = true
        }
    }

    private val paint: Paint by lazy {
        Paint().apply {
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bgRect.set(0f, 0f ,w.toFloat() , h.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRoundRect(bgRect,radius,radius,bgPaint)
        progressData?.let {
            when(orientation){
                ProgressViewOrientation.HORIZONTAL -> {
                    rect.set(0f,0f,getViewSize(),height.toFloat())
                    canvas?.drawRoundRect(rect,radius,radius, paint.apply { color = getColorFromResources(it.color) })
                }
                ProgressViewOrientation.VERTICAL -> {
                    rect.set(0f,getViewSize(),width.toFloat() ,height.toFloat())
                    canvas?.drawRoundRect(rect,radius,radius, paint.apply { color = getColorFromResources(it.color) })
                }
            }
        }
    }


    private fun getViewSize(): Float {
        var size = 0f
        when(orientation){
            ProgressViewOrientation.HORIZONTAL -> {
                size = if ((width * (progressWidth /100)) >= width){
                    width.toFloat()
                }else{
                    width * (progressWidth /100)
                }
            }
            ProgressViewOrientation.VERTICAL -> {
                size = if (height * (progressHeight /100) >= height){
                   0f
                }else{
                    height - (progressHeight / 100 * height)
                }
            }
        }
        return size
    }

    fun setData(progress: ProgressData){
        progressData = progress
        progressData?.let {
            if (autoAnimate) {
                progressAnimate(it.percentage)
            }else{
                when(orientation){
                    ProgressViewOrientation.HORIZONTAL -> {
                        progressWidth = it.percentage
                    }
                    ProgressViewOrientation.VERTICAL -> {
                        progressHeight = it.percentage
                    }
                }
                invalidate()
            }
        }
    }


    private fun progressAnimate(value: Float) {
       ObjectAnimator.ofFloat(0f, value)
                .apply {
                    duration = animationDuration
                    interpolator = progressAnimation.getInterpolator()
                    addUpdateListener {
                        when(orientation){
                            ProgressViewOrientation.HORIZONTAL -> { progressWidth = it.animatedValue as Float }
                            ProgressViewOrientation.VERTICAL -> { progressHeight = it.animatedValue as Float }
                        }
                        invalidate()
                    }
                    start()
                }
    }

    private fun getColorFromResources(color: Int): Int {
        return ContextCompat.getColor(context,color)
    }
}
