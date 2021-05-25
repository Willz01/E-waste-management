package dev.samuelmcmurray.e_wastemanagement.ui.home

import dev.samuelmcmurray.e_wastemanagement.data.model.CompanyUser
import dev.samuelmcmurray.e_wastemanagement.data.model.IndividualUser
import dev.samuelmcmurray.e_wastemanagement.data.repository.FakeRepositoryTest
import dev.samuelmcmurray.e_wastemanagement.data.singleton.CompanyUserSingleton
import dev.samuelmcmurray.e_wastemanagement.data.singleton.IndividualUserSingleton
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class HomeViewModelTest {
    private val repositoryTest = FakeRepositoryTest()
    private var companyUserMock = CompanyUser("ndjhj889231", "CompanyName",
        "This is a test", "userName", "1", "example@test.com",
        "123 Test St.", "123456789", "Sweden", "Test",
        true, true, true,true,true,
        "www.test.com")
    private var individualUserMock = IndividualUser("iwejj823jf", "Test",
        "Tester", "Tester1", "example@test.com", "10-10-2000",
        "Sweden", "Test", true)


    @Before
    fun setUp() {
        companyUserMock.rating = 4
        companyUserMock.imageURL = "imageurl.com"
        companyUserMock.hasImage = true
        individualUserMock.userProfileImageURL = "imageURL"
        individualUserMock.age = 20
        CompanyUserSingleton.getInstance.companyUser = companyUserMock
        IndividualUserSingleton.getInstance.individualUser = individualUserMock
    }

    @Test
    fun getCompanyUserPass() {
        repositoryTest.getCompanyUser(companyUserMock)
        assertEquals(CompanyUserSingleton.getInstance.companyUser, companyUserMock)
    }

    @Test
    fun getCompanyUserFail() {
        repositoryTest.getCompanyUser(individualUserMock)
        assertNotEquals(CompanyUserSingleton.getInstance.companyUser, companyUserMock)
    }

    @Test
    fun getIndividualUserPass() {
        repositoryTest.getIndividualUser(individualUserMock)
        assertEquals(IndividualUserSingleton.getInstance.individualUser, individualUserMock)
    }

    @Test
    fun getIndividualUserFail() {
        repositoryTest.getIndividualUser(companyUserMock)
        assertNotEquals(IndividualUserSingleton.getInstance.individualUser, individualUserMock)
    }

    @Test
    fun getAgePass() {
        val actualResults = repositoryTest.getAge("10-10-2000")
        val expectedResults = 20
        assertEquals(actualResults,expectedResults)
    }

    @Test
    fun getAgeFail() {
        val actualResults = repositoryTest.getAge("10-10-2000")
        val expectedResults = 21
        assertNotEquals(actualResults,expectedResults)
    }
}