package dev.samuelmcmurray.e_wastemanagement.data.model

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ItemTest {
    private lateinit var item: Item

    @Before
    fun setUp() {
        item = Item("Samsung", "1", "id", "mobile", "2018", "image1", "image2", "image3", "image4", "A2", "This is a description")

    }

    @Test
    fun getName() {
        val actualResults = item.name
        val expectedResults = "Samsung"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setName() {
        item.name = "Apple"
        val actualResults = item.name
        val expectedResults = "Apple"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getUserID() {
        val actualResults = item.userID
        val expectedResults = "1"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setUserID() {
        item.userID = "2"
        val actualResults = item.userID
        val expectedResults = "2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getId() {
        val actualResults = item.id
        val expectedResults = "id"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setId() {
        item.id = "2"
        val actualResults = item.id
        val expectedResults = "2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getType() {
        val actualResults = item.type
        val expectedResults = "mobile"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setType() {
        item.type = "component"
        val actualResults = item.type
        val expectedResults = "component"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getPurchaseYear() {
        val actualResults = item.purchaseYear
        val expectedResults = "2018"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setPurchaseYear() {
        item.purchaseYear = "2019"
        val actualResults = item.purchaseYear
        val expectedResults = "2019"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getImage1() {
        val actualResults = item.image1
        val expectedResults = "image1"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setImage1() {
        item.image1 = "image1.2"
        val actualResults = item.image1
        val expectedResults = "image1.2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getImage2() {
        val actualResults = item.image2
        val expectedResults = "image2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setImage2() {
        item.image2 = "image2.2"
        val actualResults = item.image2
        val expectedResults = "image2.2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getImage3() {
        val actualResults = item.image3
        val expectedResults = "image3"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setImage3() {
        item.image3 = "image3.2"
        val actualResults = item.image3
        val expectedResults = "image3.2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getImage4() {
        val actualResults = item.image4
        val expectedResults = "image4"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setImage4() {
        item.image4 = "image4.2"
        val actualResults = item.image4
        val expectedResults = "image4.2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getModel() {
        val actualResults = item.model
        val expectedResults = "A2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setModel() {
        item.model = "A3"
        val actualResults = item.model
        val expectedResults = "A3"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getDescription() {
        val actualResults = item.description
        val expectedResults = "This is a description"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setDescription() {
        item.description = "This is a description too"
        val actualResults = item.description
        val expectedResults = "This is a description too"
        assertEquals(expectedResults, actualResults)
    }
}