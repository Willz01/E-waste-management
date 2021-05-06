package dev.samuelmcmurray.e_wastemanagement
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class RecyclerTests {

    val appContext: Context = InstrumentationRegistry.getInstrumentation().getTargetContext()
    val list = arrayListOf<Item>()
    var recyclerViewAdapter = RecyclerViewAdapter(appContext, list)


    @Before
    fun initialize() {
        val testItem1 = Item(
            "Samsung A2",
            "zs11sa",
            "11231s",
            null,
            "Samsung",
            null,
            "Nice phone with camera issue"
        )
        val testItem2 = Item(
            "Samsung A3",
            "xxcsd34",
            "11231s",
            null,
            "Samsung",
            null,
            "Nice phone with camera charging issues"
        )

        list.add(testItem1)
        list.add(testItem2)

    }

    @Test
    fun useAppContext() {
        assertEquals("dev.samuelmcmurray.e_wastemanagement", appContext.getPackageName())
    }
    @Test
    fun testSize() {
        assert(recyclerViewAdapter.itemCount > 0)
    }
}