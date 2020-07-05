package com.abhinav.progress_view

import android.content.Context
import android.util.TypedValue
import android.util.DisplayMetrics

fun Context.dpToPx(dp : Float) : Float {
  return TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
  )
}

fun Context.pxToDp(px : Int) : Float {
  val displayMetrics = resources.displayMetrics
  return px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)
}