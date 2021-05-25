package dev.samuelmcmurray.e_wastemanagement.data.model

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class CompanyUserTest {
    private lateinit var companyUser: CompanyUser

    @Before
    fun setUp() {
        companyUser = CompanyUser("ndjhj889231", "CompanyName",
            "This is a test", "userName", "1", "example@test.com",
            "123 Test St.", "123456789", "Sweden", "Test",
            true, true, true,true,true,
            "www.test.com")
        companyUser.rating = 4
        companyUser.imageURL = "imageurl.com"
        companyUser.hasImage = true
    }

    @Test
    fun getRating() {
        val actualResults = companyUser.rating
        val expectedResults = 4
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setRating() {
        companyUser.rating = 3
        val actualResults = companyUser.rating
        val expectedResults = 3
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getImageURL() {
        val actualResults = companyUser.imageURL
        val expectedResults = "imageurl.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setImageURL() {
        companyUser.imageURL= "imageurl1.com"
        val actualResults = companyUser.imageURL
        val expectedResults = "imageurl1.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getUid() {
        val actualResults = companyUser.uid
        val expectedResults = "ndjhj889231"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setUid() {
        companyUser.uid = "newuid"
        val actualResults = companyUser.uid
        val expectedResults = "newuid"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getCompanyName() {
        val actualResults = companyUser.companyName
        val expectedResults = "CompanyName"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setCompanyName() {
        companyUser.companyName = "newCompanyName"
        val actualResults = companyUser.companyName
        val expectedResults = "newCompanyName"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getAbout() {
        val actualResults = companyUser.about
        val expectedResults = "This is a test"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setAbout() {
        companyUser.about = "This is a test too"
        val actualResults = companyUser.about
        val expectedResults = "This is a test too"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getUserName() {
        val actualResults = companyUser.userName
        val expectedResults = "userName"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setUserName() {
        companyUser.userName = "userName2"
        val actualResults = companyUser.userName
        val expectedResults = "userName2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getStoreID() {
        val actualResults = companyUser.storeID
        val expectedResults = "1"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setStoreID() {
        companyUser.storeID = "2"
        val actualResults = companyUser.storeID
        val expectedResults = "2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getEmail() {
        val actualResults = companyUser.email
        val expectedResults = "example@test.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setEmail() {
        companyUser.email = "example2@test.com"
        val actualResults = companyUser.email
        val expectedResults = "example2@test.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getAddress() {
        val actualResults = companyUser.address
        val expectedResults = "123 Test St."
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setAddress() {
        companyUser.address = "1234 Test St."
        val actualResults = companyUser.address
        val expectedResults = "1234 Test St."
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getPhoneNumber() {
        val actualResults = companyUser.phoneNumber
        val expectedResults = "123456789"
        assertEquals(expectedResults, actualResults)

    }

    @Test
    fun setPhoneNumber() {
        companyUser.phoneNumber = "987654321"
        val actualResults = companyUser.phoneNumber
        val expectedResults = "987654321"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getCountry() {
        val actualResults = companyUser.country
        val expectedResults = "Sweden"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setCountry() {
        companyUser.country = "US"
        val actualResults = companyUser.country
        val expectedResults = "US"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getCity() {
        val actualResults = companyUser.city
        val expectedResults = "Test"
        assertEquals(expectedResults, actualResults)

    }

    @Test
    fun setCity() {
        companyUser.city = "City"
        val actualResults = companyUser.city
        val expectedResults = "City"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getHasRecycling() {
        val actualResults = companyUser.hasRecycling
        val expectedResults = true
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setHasRecycling() {
        companyUser.hasRecycling = false
        val actualResults = companyUser.hasRecycling
        val expectedResults = false
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getHasImage() {
        val actualResults = companyUser.hasImage
        val expectedResults = true
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setHasImage() {
        companyUser.hasImage = false
        val actualResults = companyUser.hasImage
        val expectedResults = false
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getTakesMobiles() {
        val actualResults = companyUser.takesMobiles
        val expectedResults = true
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setTakesMobiles() {
        companyUser.takesMobiles = false
        val actualResults = companyUser.takesMobiles
        val expectedResults = false
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getTakesComponents() {
        val actualResults = companyUser.takesComponents
        val expectedResults = true
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setTakesComponents() {
        companyUser.takesComponents = false
        val actualResults = companyUser.takesComponents
        val expectedResults = false
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getTakesOther() {
        val actualResults = companyUser.takesOther
        val expectedResults = true
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setTakesOther() {
        companyUser.takesOther = false
        val actualResults = companyUser.takesOther
        val expectedResults = false
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getWebsiteURL() {
        val actualResults = companyUser.websiteURL
        val expectedResults = "www.test.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setWebsiteURL() {
        companyUser.websiteURL = "www.test2.com"
        val actualResults = companyUser.websiteURL
        val expectedResults = "www.test2.com"
        assertEquals(expectedResults, actualResults)
    }
}