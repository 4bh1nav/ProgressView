package com.abhinav.progress_view

import android.view.animation.*

enum class ProgressViewAnimation(val value: Int) {

    NORMAL(0),
    BOUNCE(1),
    DECELERATE(2),
    ACCELERATEDECELERATE(3);

    fun getInterpolator(): Interpolator {
        return when (value) {
            BOUNCE.value -> BounceInterpolator()
            DECELERATE.value -> DecelerateInterpolator()
            ACCELERATEDECELERATE.value -> AccelerateDecelerateInterpolator()
            else -> AccelerateInterpolator()
        }
    }
}