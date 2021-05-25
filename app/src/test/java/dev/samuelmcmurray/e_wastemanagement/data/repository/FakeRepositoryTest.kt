package dev.samuelmcmurray.e_wastemanagement.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import dev.samuelmcmurray.e_wastemanagement.data.model.CompanyUser
import dev.samuelmcmurray.e_wastemanagement.data.model.IndividualUser
import dev.samuelmcmurray.e_wastemanagement.data.singleton.CompanyUserSingleton
import dev.samuelmcmurray.e_wastemanagement.data.singleton.IndividualUserSingleton
import java.time.LocalDate
import java.time.Period

class FakeRepositoryTest {
    val usersMock = listOf(User("example@test.com", "123456"))

    fun getCompanyUser(user: Any ) {
        if (CompanyUserSingleton.getInstance.companyUser != user) {
            CompanyUserSingleton.getInstance.companyUser = null
        }
    }

    fun getIndividualUser(user: Any ) {
        if (IndividualUserSingleton.getInstance.individualUser != user) {
            IndividualUserSingleton.getInstance.individualUser = null
        }
    }

    fun login(email: String, password: String): Boolean {
        if (password.length > 5) {
            for (user in usersMock) {
                if (user.email == email && user.password == password) {
                    return true
                }
            }
        }
        return false
    }

    fun resetPassword(email: String): Boolean {
        for (user in usersMock) {
            if (user.email == email) {
                return true
            }
        }
        return false
    }

    fun registerEmail(email: String, password: String): Boolean {
        if (password.length > 5) {
            for (user in usersMock) {
                if (user.email != email) {
                    return true
                }
            }
        }
        return false
    }

    fun createIndividualUser(firstName: String, lastName: String, userName: String, email: String, city: String,
        country: String, dob: String) {
        var uid = "1"
        val individualUser = IndividualUser(
            uid,
            firstName,
            lastName,
            userName,
            email,
            dob,
            country,
            city,
            false
            )

        IndividualUserSingleton.getInstance.individualUser = individualUser
    }

    fun createCompanyUser(companyName: String, userName: String, storeID: String,
                          email: String, address: String, phoneNumber: String, country: String,
                          city: String, hasRecycling: Boolean, takesMobiles: Boolean,
                          takesComponents: Boolean, takesOther: Boolean, websiteURL: String) {
        var uid = "1"
        val about = ""
        val companyUser = CompanyUser(
            uid,
            companyName,
            about,
            userName,
            storeID,
            email,
            address,
            phoneNumber,
            country,
            city,
            hasRecycling,
            false,
            takesMobiles,
            takesComponents,
            takesOther,
            websiteURL
            )

        CompanyUserSingleton.getInstance.companyUser = companyUser
    }

    fun emailVerification(boolean: Boolean): Boolean {
        if (boolean) {
            return true
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAge(dob: String) : Int {
        val parts = dob.split("-")
        val year = parts[2]
        var month = parts[1]
        if (month.length < 2) {
            month = "0$month"
        }
        val day = parts[0]
        return Period.between(
            LocalDate.of(year.toInt(), month.toInt(), day.toInt()),
            LocalDate.now()
        ).years
    }
}

class User(var email: String, var password: String){

}