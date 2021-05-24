package dev.samuelmcmurray.e_wastemanagement.data.singleton

import dev.samuelmcmurray.e_wastemanagement.data.model.IndividualUser
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class IndividualUserSingletonTest {
    private var individualUserSingleton =  IndividualUserSingleton

    @Before
    fun setUp() {
        individualUserSingleton.getInstance.individualUser = IndividualUser("iwejj823jf",
            "Test", "Tester", "Tester1", "example@test.com",
            "10-10-2000", "Sweden", "Test", true)
        individualUserSingleton.getInstance.individualUser!!.userProfileImageURL = "imageURL"
        individualUserSingleton.getInstance.individualUser!!.age = 20
    }

    @Test
    fun getIndividualUser() {
        val actualResults = IndividualUserSingleton.getInstance.individualUser
        val expectedResults = individualUserSingleton.getInstance.individualUser
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setIndividualUser() {
        val individualUser = IndividualUser("iwejj823jf3",
            "Test1", "Tester1", "Tester12", "example2@test.com",
            "10-11-2000", "US", "Test2", false)
        individualUser.userProfileImageURL = "2"
        individualUser.age = 21
        IndividualUserSingleton.getInstance.individualUser = individualUser
        val actualResults = IndividualUserSingleton.getInstance.individualUser
        val expectedResults = individualUserSingleton.getInstance.individualUser
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getUserProfileImageURL() {
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.userProfileImageURL
        val expectedResults = "imageURL"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setUserProfileImageURL() {
        IndividualUserSingleton.getInstance.individualUser!!.userProfileImageURL = "imageURL2"
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.userProfileImageURL
        val expectedResults = "imageURL2"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getAge() {
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.age
        val expectedResults = 20
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setAge() {
        IndividualUserSingleton.getInstance.individualUser!!.age = 21
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.age
        val expectedResults = 21
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getUid() {
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.uid
        val expectedResults = "iwejj823jf"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setUid() {
        IndividualUserSingleton.getInstance.individualUser!!.uid = "newUID"
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.uid
        val expectedResults = "newUID"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getFirstName() {
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.firstName
        val expectedResults = "Test"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setFirstName() {
        IndividualUserSingleton.getInstance.individualUser!!.firstName = "Tester"
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.firstName
        val expectedResults = "Tester"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getLastName() {
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.lastName
        val expectedResults = "Tester"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setLastName() {
        IndividualUserSingleton.getInstance.individualUser!!.lastName = "Test"
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.lastName
        val expectedResults = "Test"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getUserName() {
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.userName
        val expectedResults = "Tester1"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setUserName() {
        IndividualUserSingleton.getInstance.individualUser!!.userName = "Test"
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.userName
        val expectedResults = "Test"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getEmail() {
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.email
        val expectedResults = "example@test.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setEmail() {
        IndividualUserSingleton.getInstance.individualUser!!.email = "example2@test.com"
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.email
        val expectedResults = "example2@test.com"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getDob() {
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.dob
        val expectedResults = "10-10-2000"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setDob() {
        IndividualUserSingleton.getInstance.individualUser!!.dob = "10-11-2000"
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.dob
        val expectedResults = "10-11-2000"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getCountry() {
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.country
        val expectedResults = "Sweden"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setCountry() {
        IndividualUserSingleton.getInstance.individualUser!!.country = "US"
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.country
        val expectedResults = "US"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getCity() {
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.city
        val expectedResults = "Test"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setCity() {
        IndividualUserSingleton.getInstance.individualUser!!.city = "Kristianstad"
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.city
        val expectedResults = "Kristianstad"
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun getHasImage() {
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.hasImage
        val expectedResults = true
        assertEquals(expectedResults, actualResults)
    }

    @Test
    fun setHasImage() {
        IndividualUserSingleton.getInstance.individualUser!!.hasImage = false
        val actualResults = IndividualUserSingleton.getInstance.individualUser!!.hasImage
        val expectedResults = false
        assertEquals(expectedResults, actualResults)
    }
}