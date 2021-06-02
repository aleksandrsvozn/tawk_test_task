package com.alexvoz.tawk_test_task.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

/**
 *  Change View visibility
 *
 *  @param state Boolean
 */
fun View.changeVisibility(state: Boolean) {
    this.visibility = if (state) VISIBLE else GONE
}
