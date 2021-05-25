package dev.samuelmcmurray.e_wastemanagement.ui.auth

import dev.samuelmcmurray.e_wastemanagement.data.model.CompanyUser
import dev.samuelmcmurray.e_wastemanagement.data.model.IndividualUser
import dev.samuelmcmurray.e_wastemanagement.data.repository.FakeRepositoryTest
import dev.samuelmcmurray.e_wastemanagement.data.singleton.CompanyUserSingleton
import dev.samuelmcmurray.e_wastemanagement.data.singleton.IndividualUserSingleton
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class RegisterViewModelTest {
    private val repositoryTest = FakeRepositoryTest()
    private var companyUserMock = CompanyUser("ndjhj889231", "CompanyName",
        "This is a test", "userName", "1", "example2@test.com",
        "123 Test St.", "123456789", "Sweden", "Test",
        true, true, true,true,true,
        "www.test.com")
    private var individualUserMock = IndividualUser("iwejj823jf", "Test",
        "Tester", "Tester1", "example@test2.com", "10-10-2000",
        "Sweden", "Test", true)


    @Before
    fun setUp() {
        companyUserMock.rating = 4
        companyUserMock.imageURL = "imageurl.com"
        companyUserMock.hasImage = true
        individualUserMock.userProfileImageURL = "imageURL"
        individualUserMock.age = 20
        CompanyUserSingleton.getInstance.companyUser = null
        IndividualUserSingleton.getInstance.individualUser = null
    }

    @Test
    fun registerIndividualUserPass() {
        if (repositoryTest.registerEmail(individualUserMock.email, "123456")) {
            repositoryTest.createIndividualUser(individualUserMock.firstName, individualUserMock.lastName,
            individualUserMock.userName, individualUserMock.email, individualUserMock.country,
                individualUserMock.city, individualUserMock.dob)
        }
        assertNotEquals(null, IndividualUserSingleton.getInstance.individualUser)
    }

    @Test
    fun registerIndividualUserFail() {
        if (repositoryTest.registerEmail(individualUserMock.email, "")) {
            repositoryTest.createIndividualUser(individualUserMock.firstName, individualUserMock.lastName,
                individualUserMock.userName, individualUserMock.email, individualUserMock.country,
                individualUserMock.city, individualUserMock.dob)
        }
        assertEquals(null, IndividualUserSingleton.getInstance.individualUser)
    }

    @Test
    fun registerCompanyUserPass() {
        if (repositoryTest.registerEmail(companyUserMock.email, "123456")) {
            repositoryTest.createCompanyUser(companyUserMock.companyName, companyUserMock.userName,
                companyUserMock.storeID, companyUserMock.email, companyUserMock.address,
                companyUserMock.phoneNumber, companyUserMock.country, companyUserMock.city,
                companyUserMock.hasRecycling, companyUserMock.takesMobiles,
                companyUserMock.takesComponents, companyUserMock.takesOther,
                companyUserMock.websiteURL)
        }
        assertNotEquals(null, CompanyUserSingleton.getInstance.companyUser)
    }

    @Test
    fun registerCompanyUserFail() {
        if (repositoryTest.registerEmail(companyUserMock.email, "")) {
            repositoryTest.createCompanyUser(companyUserMock.companyName, companyUserMock.userName,
                companyUserMock.storeID, companyUserMock.email, companyUserMock.address,
                companyUserMock.phoneNumber, companyUserMock.country, companyUserMock.city,
                companyUserMock.hasRecycling, companyUserMock.takesMobiles,
                companyUserMock.takesComponents, companyUserMock.takesOther,
                companyUserMock.websiteURL)
        }
        assertEquals(null, CompanyUserSingleton.getInstance.companyUser)
    }

    @Test
    fun emailVerificationPass() {
        val actualResults = repositoryTest.emailVerification(
            repositoryTest.login("example@test.com", "123456"))
        val expectedResults = true
        assertEquals(actualResults, expectedResults)
    }

    @Test
    fun emailVerificationFail() {
        val actualResults = repositoryTest.emailVerification(
            repositoryTest.login("example@test.com", ""))
        val expectedResults = false
        assertEquals(actualResults, expectedResults)
    }
}