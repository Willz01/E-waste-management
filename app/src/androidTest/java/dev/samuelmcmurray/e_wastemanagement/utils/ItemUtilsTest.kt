package dev.samuelmcmurray.e_wastemanagement.utils

import dev.samuelmcmurray.e_wastemanagement.data.model.Item
import org.junit.Assert
import org.junit.Test

class ItemUtilsTest {

    private val item = Item(
        "iPhone 5s", "userID", "xxxx", "2008",
        "5S", "no image yet", "Old phone with crack screen, but in working condition"
    )

    @Test
    fun addItemToFirebase() {
        val testItem = item
        Assert.assertTrue(ItemUtils.newInstance().addItemToFirebase(testItem))
    }

    @Test
    fun readItemsFromFirebase() {
        Assert.assertTrue(ItemUtils.newInstance().readItemsFromFirebase(object : ItemsCallback {
            override fun onCallback(value: ArrayList<Item>) {
            }
        }))
    }

}