package dev.samuelmcmurray.e_wastemanagement

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.samuelmcmurray.e_wastemanagement.data.model.Item
import dev.samuelmcmurray.e_wastemanagement.utils.ItemUtils
import dev.samuelmcmurray.e_wastemanagement.utils.ItemsCallback
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*


@RunWith(AndroidJUnit4::class)
class FireBaseTests {

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val itemUtils = ItemUtils()

    /**
     * Adding null images will cause an error in the recyclerview(null pointer exception in HomeFragment) on runtime
     */
    private val testItem1 = Item(
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

    @Test
    fun useAppContext() {
        assertEquals("dev.samuelmcmurray.e_wastemanagement", appContext.packageName)
    }

    @Test
    fun addToDB() {
        val result = itemUtils.addItemToFirebase(testItem1)

        assertEquals(true, result)
    }

    @Test
    fun addItemToFirebase() {
        assertTrue(
            ItemUtils.newInstance()
                .addItemToFirebase(testItem1)
        )
    }

    @Test
    fun readItemsFromFirebase() {
        assertTrue(
            ItemUtils.newInstance()
                .readItemsFromFirebase(object :
                    ItemsCallback {
                    override fun onCallback(value: ArrayList<Item>) {
                    }
                })
        )
    }


}
