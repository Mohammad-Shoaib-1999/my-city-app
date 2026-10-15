package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.ExploreRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Explore Nanded", appName)
    }

    @Test
    fun `repository loads with application context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = ExploreRepository(context)
        assertNotNull(repo.places)
        assertEquals("Takht Sachkhand Sri Hazur Abchalnagar Sahib", repo.getPlaceById("hazur-sahib")?.name)
    }
}
