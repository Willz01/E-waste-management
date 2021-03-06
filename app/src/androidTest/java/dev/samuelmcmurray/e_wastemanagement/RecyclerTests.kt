package dev.samuelmcmurray.e_wastemanagement

import android.content.Context
import android.view.View
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.samuelmcmurray.e_wastemanagement.adapters.RecyclerViewAdapter
import dev.samuelmcmurray.e_wastemanagement.data.model.Item
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class RecyclerTests {
    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val list = arrayListOf<Item>()
    private var recyclerViewAdapter = RecyclerViewAdapter(appContext, list, view = View(appContext))

    /**
     * Adding null images will cause an error in the recyclerview(null pointer exception in HomeFragment) on runtime
     */
    @Before
    fun initialize() {
        val testItem1 = Item(
            "Samsung A2",
            "zs11sa",
            "2315",
            "11231s",
            "1955",
            null,
            null,
            null,
            null,
            "samsung",
            "very nice thing"
        )
        val testItem2 = Item(
            "Samsung A2",
            "zs11sa",
            "2315",
            "11231s",
            "1955",
            null,
            null,
            null,
            null,
            "samsung",
            "very nice thing"
        )

        list.add(testItem1)
        list.add(testItem2)

    }

    @Test
    fun useAppContext() {
        assertEquals("dev.samuelmcmurray.e_wastemanagement", appContext.packageName)
    }
    @Test
    fun testSize() {
        assert(recyclerViewAdapter.itemCount > 0)
    }
}