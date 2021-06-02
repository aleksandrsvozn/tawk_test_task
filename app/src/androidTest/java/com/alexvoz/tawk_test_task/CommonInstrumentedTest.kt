package com.alexvoz.tawk_test_task

import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.alexvoz.tawk_test_task.utils.getDrawableImage
import com.google.common.truth.Truth
import org.junit.Assert.assertEquals
import org.junit.Test

@SmallTest
class CommonInstrumentedTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.alexvoz.tawk_test_task", appContext.packageName)
    }

    @Test
    fun getDrawableImage() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val utilImage = appContext.getDrawableImage(R.drawable.ic_launcher_foreground)?.toBitmap()
        val realImage =
            ContextCompat.getDrawable(appContext, R.drawable.ic_launcher_foreground)?.toBitmap()

        Truth.assertThat(utilImage?.byteCount).isEqualTo(realImage?.byteCount)
    }
}