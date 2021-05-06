package dev.samuelmcmurray.e_wastemanagement
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class FireBaseTests {
    val appContext: Context = InstrumentationRegistry.getInstrumentation().getTargetContext()

    val itemUtils = ItemUtils()


    @Test
    fun useAppContext() {
        assertEquals("dev.samuelmcmurray.e_wastemanagement", appContext.getPackageName())
    }

    @Test
    fun addToDB() {
        val testItem1 = Item(
            "Samsung A2",
            "zs11sa",
            "11231s",
            null,
            "Samsung",
            null,
            "Nice phone with camera issue"
        )
        val result = itemUtils.addItemToFirebase(testItem1)

        assertEquals(true, result)
    }

}