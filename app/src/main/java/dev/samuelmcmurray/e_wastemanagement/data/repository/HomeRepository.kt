package dev.samuelmcmurray.e_wastemanagement.data.repository

import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.samuelmcmurray.e_wastemanagement.data.model.CompanyUser
import dev.samuelmcmurray.e_wastemanagement.data.model.IndividualUser
import dev.samuelmcmurray.e_wastemanagement.data.singleton.CompanyUserSingleton
import dev.samuelmcmurray.e_wastemanagement.data.singleton.IndividualUserSingleton
import java.time.LocalDate
import java.time.Period

private const val TAG = "HomeRepository"
class HomeRepository {
    private var application: Application
    private var firebaseAuth: FirebaseAuth
    var companyUserLiveData: MutableLiveData<Boolean>
    var individualUserLiveData: MutableLiveData<Boolean>

    constructor(application: Application) {
        this.application = application
        this.firebaseAuth = FirebaseAuth.getInstance()
        this.companyUserLiveData = MutableLiveData()
        this.individualUserLiveData = MutableLiveData()
    }

    fun getCompanyUser() {
        val companyRef = FirebaseFirestore.getInstance().collection("CompanyUsers")
        val userID = firebaseAuth.currentUser?.uid
        companyRef.document(userID.toString()).get().addOnSuccessListener {
            result ->
            if (result.exists()) {
                val companyName = result["companyName"].toString()
                val about = result["about"].toString()
                val userName = result["userName"].toString()
                val storeID = result["storeID"].toString()
                val email = result["email"].toString()
                val address = result["address"].toString()
                val phoneNumber = result["phoneNumber"].toString()
                val country = result["country"].toString()
                val city = result["city"].toString()
                val hasRecycling = result["hasRecycling"].toString().toBoolean()
                val hasImage = result["hasImage"].toString().toBoolean()
                val takesMobiles = result["takesMobiles"].toString().toBoolean()
                val takesComponents = result["takesComponents"].toString().toBoolean()
                val takesOther = result["takesOther"].toString().toBoolean()
                val websiteURL = result["websiteURL"].toString()

                val companyUser = CompanyUser(
                    userID.toString(),
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
                    hasImage,
                    takesMobiles,
                    takesComponents,
                    takesOther,
                    websiteURL
                )

                CompanyUserSingleton.getInstance.companyUser = companyUser
                companyUserLiveData.postValue(true)
                individualUserLiveData.postValue(false)
                Log.d(TAG, "getCompanyUser: Success")
                Toast.makeText(
                    application.applicationContext, "Welcome $companyName",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                companyUserLiveData.postValue(false)
            }
        }.addOnFailureListener {
            exception ->
            Log.d(TAG, "getCompanyUser: ${exception.message}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getIndividualUser() {
        val individualRef = FirebaseFirestore.getInstance().collection("IndividualUsers")
        val userID = firebaseAuth.currentUser?.uid
        individualRef.document(userID.toString()).get().addOnSuccessListener {
            result->
            if (result.exists()) {
                val firstName = result["firstName"].toString()
                val lastName = result["lastName"].toString()
                val userName = result["userName"].toString()
                val email = result["email"].toString()
                val dateOfBirth = result["dateOfBirth"].toString()
                val hasImage = result["hasImage"].toString().toBoolean()
                val userProfileImageURL = result["userImageURL"].toString()
                val city = result["city"].toString()
                val country = result["country"].toString()

                val individualUser = IndividualUser(
                    userID.toString(), firstName, lastName, userName,
                    email, dateOfBirth, country, city, hasImage
                )
                individualUser.age = getAge(dateOfBirth)
                individualUser.userProfileImageURL = userProfileImageURL

                IndividualUserSingleton.getInstance.individualUser = individualUser
                individualUserLiveData.postValue(true)
                Log.d(TAG, "getIndividualUser: Success")
                Toast.makeText(
                    application.applicationContext, "Welcome $firstName",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                individualUserLiveData.postValue(false)
            }
        }.addOnFailureListener {
            exception ->
            Log.d(TAG, "getIndividualUser: ${exception.message}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAge(dob: String) : Int {
        val parts = dob.split("-")
        val year = parts[2]
        val month = parts[0]
        val day = parts[1]
        return Period.between(
            LocalDate.of(year.toInt(), month.toInt(), day.toInt()),
            LocalDate.now()
        ).years
    }
}