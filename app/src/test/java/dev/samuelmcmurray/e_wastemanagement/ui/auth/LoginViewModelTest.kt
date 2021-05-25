package dev.samuelmcmurray.e_wastemanagement.ui.auth

import dev.samuelmcmurray.e_wastemanagement.data.repository.FakeRepositoryTest
import org.junit.Test

import org.junit.Assert.*

class LoginViewModelTest {
    private val repositoryTest = FakeRepositoryTest()
    private val email = "example@test.com"
    private val password = "123456"

    @Test
    fun loginPass() {
        val expectedResults = true
        val actualResults = repositoryTest.login(email,password)
        assertEquals(actualResults, expectedResults)
    }

    @Test
    fun loginFail() {
        val expectedResults = false
        val actualResults = repositoryTest.login(email, "")
        assertEquals(actualResults, expectedResults)
    }

    @Test
    fun resetPasswordPass() {
        val expectedResults = true
        val actualResults = repositoryTest.resetPassword(email)
        assertEquals(actualResults, expectedResults)
    }

    @Test
    fun resetPasswordFail() {
        val expectedResults = false
        val actualResults = repositoryTest.resetPassword("email")
        assertEquals(actualResults, expectedResults)
    }
}