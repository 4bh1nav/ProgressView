package com.abhinav.progress_view

import androidx.annotation.ColorRes

/**
 * Data class representing progress of the [ProgressView] containing [Float] value, [Float] Total value, name and color.
 */
data class ProgressData(val name: String, var value: Float ,val total: Float , @ColorRes val color: Int){
    val percentage = if (value >= total) {
        100f
    }else {
        value / total * 100f
    }
}