package dev.samuelmcmurray.e_wastemanagement.data.singleton

import dev.samuelmcmurray.e_wastemanagement.data.model.CompanyUser
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class CompanyUserSingletonTest {
    private var companyUserSingleton = CompanyUserSingleton

    @Before
    fun setUp() {
        companyUserSingleton.getInstance.companyUser = CompanyUser("ndjhj889231", "CompanyName",
            "This is a test", "userName", "1", "example@test.com",
            "123 Test St.", "123456789", "Sweden", "Test",
            true, true, true,true,true,
            "www.test.com")
        companyUserSingleton.getInstance.companyUser!!.rating = 4
        companyUserSingleton.getInstance.companyUser!!.imageURL = "imageurl.com"
        companyUserSingleton.getInstance.companyUser!!.hasImage = true
    }

    @Test
    fun getCompanyUser() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser
        val expectedResults = companyUserSingleton.getInstance.companyUser
        assertEquals(expectedResults, actualResults)

    }

    @Test
    fun setCompanyUser() {
        val companyUser = CompanyUser("ndjhj8892312", "CompanyName2",
            "This is a test too", "userName2", "12", "example2@test.com",
            "1234 Test St.", "987654321", "US", "Kristianstad",
            false, false, false,false,false,
            "www.test2.com")
        CompanyUserSingleton.getInstance.companyUser = companyUser
        val actualResults = CompanyUserSingleton.getInstance.companyUser
        val expectedResults = companyUserSingleton.getInstance.companyUser
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getRating() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.rating
        val expectedResults = 4
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setRating() {
        CompanyUserSingleton.getInstance.companyUser!!.rating = 3
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.rating
        val expectedResults = 3
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getImageURL() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.imageURL
        val expectedResults = "imageurl.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setImageURL() {
        CompanyUserSingleton.getInstance.companyUser!!.imageURL= "imageurl1.com"
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.imageURL
        val expectedResults = "imageurl1.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getUid() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.uid
        val expectedResults = "ndjhj889231"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setUid() {
        CompanyUserSingleton.getInstance.companyUser!!.uid = "newuid"
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.uid
        val expectedResults = "newuid"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getCompanyName() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.companyName
        val expectedResults = "CompanyName"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setCompanyName() {
        CompanyUserSingleton.getInstance.companyUser!!.companyName = "newCompanyName"
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.companyName
        val expectedResults = "newCompanyName"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getAbout() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.about
        val expectedResults = "This is a test"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setAbout() {
        CompanyUserSingleton.getInstance.companyUser!!.about = "This is a test too"
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.about
        val expectedResults = "This is a test too"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getUserName() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.userName
        val expectedResults = "userName"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setUserName() {
        CompanyUserSingleton.getInstance.companyUser!!.userName = "userName2"
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.userName
        val expectedResults = "userName2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getStoreID() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.storeID
        val expectedResults = "1"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setStoreID() {
        CompanyUserSingleton.getInstance.companyUser!!.storeID = "2"
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.storeID
        val expectedResults = "2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getEmail() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.email
        val expectedResults = "example@test.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setEmail() {
        CompanyUserSingleton.getInstance.companyUser!!.email = "example2@test.com"
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.email
        val expectedResults = "example2@test.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getAddress() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.address
        val expectedResults = "123 Test St."
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setAddress() {
        CompanyUserSingleton.getInstance.companyUser!!.address = "1234 Test St."
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.address
        val expectedResults = "1234 Test St."
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getPhoneNumber() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.phoneNumber
        val expectedResults = "123456789"
        assertEquals(expectedResults, actualResults)

    }

    @Test
    fun setPhoneNumber() {
        CompanyUserSingleton.getInstance.companyUser!!.phoneNumber = "987654321"
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.phoneNumber
        val expectedResults = "987654321"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getCountry() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.country
        val expectedResults = "Sweden"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setCountry() {
        CompanyUserSingleton.getInstance.companyUser!!.country = "US"
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.country
        val expectedResults = "US"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getCity() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.city
        val expectedResults = "Test"
        assertEquals(expectedResults, actualResults)

    }

    @Test
    fun setCity() {
        CompanyUserSingleton.getInstance.companyUser!!.city = "City"
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.city
        val expectedResults = "City"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getHasRecycling() {
        val actualResults =CompanyUserSingleton.getInstance.companyUser!!.hasRecycling
        val expectedResults = true
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setHasRecycling() {
        CompanyUserSingleton.getInstance.companyUser!!.hasRecycling = false
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.hasRecycling
        val expectedResults = false
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getHasImage() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.hasImage
        val expectedResults = true
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setHasImage() {
        CompanyUserSingleton.getInstance.companyUser!!.hasImage = false
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.hasImage
        val expectedResults = false
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getTakesMobiles() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.takesMobiles
        val expectedResults = true
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setTakesMobiles() {
        CompanyUserSingleton.getInstance.companyUser!!.takesMobiles = false
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.takesMobiles
        val expectedResults = false
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getTakesComponents() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.takesComponents
        val expectedResults = true
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setTakesComponents() {
        CompanyUserSingleton.getInstance.companyUser!!.takesComponents = false
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.takesComponents
        val expectedResults = false
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getTakesOther() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.takesOther
        val expectedResults = true
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setTakesOther() {
        CompanyUserSingleton.getInstance.companyUser!!.takesOther = false
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.takesOther
        val expectedResults = false
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getWebsiteURL() {
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.websiteURL
        val expectedResults = "www.test.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setWebsiteURL() {
        CompanyUserSingleton.getInstance.companyUser!!.websiteURL = "www.test2.com"
        val actualResults = CompanyUserSingleton.getInstance.companyUser!!.websiteURL
        val expectedResults = "www.test2.com"
        assertEquals(expectedResults, actualResults)
    }
}