package com.alexvoz.tawk_test_task.utils

import android.app.Activity
import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

val INVERTED_MATRIX = floatArrayOf(
    -1.0f, 0.0f, 0.0f, 0.0f, 255.0f,
    0.0f, -1.0f, 0.0f, 0.0f, 255.0f,
    0.0f, 0.0f, -1.0f, 0.0f, 255.0f,
    0.0f, 0.0f, 0.0f, 1.0f, 0.0f
)

object Util {

    /**
     *  @return colorMatrixColorFilter inverted color filter
     */
    fun getInvertedColorFilter(): ColorMatrixColorFilter {

        val matrixInvert = ColorMatrix().apply {
            set(INVERTED_MATRIX)
        }
        return ColorMatrixColorFilter(matrixInvert)
    }
}

fun Context.getDrawableImage(drawableId: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableId)

fun Fragment.hideKeyBoard() {
    (this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(this.requireView().windowToken, 0)
}