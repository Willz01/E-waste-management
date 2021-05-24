package dev.samuelmcmurray.e_wastemanagement.data.model

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class IndividualUserTest {
    private lateinit var individualUser: IndividualUser

    @Before
    fun setUp() {
        individualUser = IndividualUser("iwejj823jf", "Test", "Tester", "Tester1", "example@test.com", "10-10-2000", "Sweden", "Test", true)
        individualUser.userProfileImageURL = "imageURL"
        individualUser.age = 20
    }

    @Test
    fun getUserProfileImageURL() {
        val actualResults = individualUser.userProfileImageURL
        val expectedResults = "imageURL"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setUserProfileImageURL() {
        individualUser.userProfileImageURL = "imageURL2"
        val actualResults = individualUser.userProfileImageURL
        val expectedResults = "imageURL2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getAge() {
        val actualResults = individualUser.age
        val expectedResults = 20
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setAge() {
        individualUser.age = 21
        val actualResults = individualUser.age
        val expectedResults = 21
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getUid() {
        val actualResults = individualUser.uid
        val expectedResults = "iwejj823jf"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setUid() {
        individualUser.uid = "newUID"
        val actualResults = individualUser.uid
        val expectedResults = "newUID"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getFirstName() {
        val actualResults = individualUser.firstName
        val expectedResults = "Test"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setFirstName() {
        individualUser.firstName = "Tester"
        val actualResults = individualUser.firstName
        val expectedResults = "Tester"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getLastName() {
        val actualResults = individualUser.lastName
        val expectedResults = "Tester"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setLastName() {
        individualUser.lastName = "Test"
        val actualResults = individualUser.lastName
        val expectedResults = "Test"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getUserName() {
        val actualResults = individualUser.userName
        val expectedResults = "Tester1"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setUserName() {
        individualUser.userName = "Test"
        val actualResults = individualUser.userName
        val expectedResults = "Test"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getEmail() {
        val actualResults = individualUser.email
        val expectedResults = "example@test.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setEmail() {
        individualUser.email = "example2@test.com"
        val actualResults = individualUser.email
        val expectedResults = "example2@test.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getDob() {
        val actualResults = individualUser.dob
        val expectedResults = "10-10-2000"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setDob() {
        individualUser.dob = "10-11-2000"
        val actualResults = individualUser.dob
        val expectedResults = "10-11-2000"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getCountry() {
        val actualResults = individualUser.country
        val expectedResults = "Sweden"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setCountry() {
        individualUser.country = "US"
        val actualResults = individualUser.country
        val expectedResults = "US"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getCity() {
        val actualResults = individualUser.city
        val expectedResults = "Test"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setCity() {
        individualUser.city = "Kristianstad"
        val actualResults = individualUser.city
        val expectedResults = "Kristianstad"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getHasImage() {
        val actualResults = individualUser.hasImage
        val expectedResults = true
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setHasImage() {
        individualUser.hasImage = false
        val actualResults = individualUser.hasImage
        val expectedResults = false
        assertEquals(expectedResults, actualResults)
    }
}