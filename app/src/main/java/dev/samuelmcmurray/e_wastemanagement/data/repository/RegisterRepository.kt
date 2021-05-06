package dev.samuelmcmurray.e_wastemanagement.data.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dev.samuelmcmurray.e_wastemanagement.data.model.CompanyUser
import dev.samuelmcmurray.e_wastemanagement.data.singleton.IndividualUserSingleton
import dev.samuelmcmurray.e_wastemanagement.data.model.IndividualUser
import dev.samuelmcmurray.e_wastemanagement.data.singleton.CompanyUserSingleton

private const val TAG = "RegisterRepository"
class RegisterRepository {
    private var application: Application
    private var firebaseAuth: FirebaseAuth
    private val firebaseApplication = FirebaseApp.getInstance()
    var userLiveData: MutableLiveData<FirebaseUser>
    var loggedOutLiveData: MutableLiveData<Boolean>
    var userCreatedLiveData: MutableLiveData<Boolean>
    var emailSentLiveData: MutableLiveData<Boolean>


    constructor(application: Application) {
        this.application = application
        firebaseAuth = FirebaseAuth.getInstance()
        userLiveData = MutableLiveData()
        loggedOutLiveData = MutableLiveData()
        userCreatedLiveData = MutableLiveData()
        emailSentLiveData = MutableLiveData()

        if (firebaseAuth.currentUser != null) {
            userLiveData.postValue(firebaseAuth.currentUser);
            loggedOutLiveData.postValue(false);
        }
    }

    fun registerEmail(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                ContextCompat.getMainExecutor(application),
                { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = firebaseAuth.currentUser
                        userLiveData.postValue(firebaseUser)
                        Log.d(TAG, "onComplete: " + firebaseUser?.uid)
                    } else {
                        Toast.makeText(
                            application.applicationContext,
                            "Registration Failure: " + (task.exception?.message
                                ?: "failed"),
                            Toast.LENGTH_SHORT
                        ).show()
                        loggedOutLiveData.postValue(true)
                    }
                })
    }

    fun createIndividualUser(
        firstName: String, lastName: String, userName: String, email: String, city: String,
        country: String, dob: String) {
        var uid = firebaseAuth.currentUser?.uid
        while (uid == null) {
            //TODO: fix so it cant go into an infinite loop
            uid = firebaseAuth.currentUser?.uid
        }

        val individualUser =
            IndividualUser(
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

        val user = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "userName" to userName,
            "email" to email,
            "country" to country,
            "city" to city,
            "dateOfBirth" to dob,
            "hasImage" to false
        )
        Log.d(TAG, "createIndividualUser: $user")

        val db = FirebaseFirestore.getInstance(firebaseApplication)
        db.collection("IndividualUsers")
            .document("$uid")
            .set(user)
            .addOnSuccessListener {
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: $uid"
                )
                IndividualUserSingleton.getInstance.individualUser = individualUser
                userCreatedLiveData.postValue(true)
            }
            .addOnFailureListener { e -> Log.i(TAG, "Error adding document $e", e)
                userCreatedLiveData.postValue(false)}


    }

    fun createCompanyUser(companyName: String, userName: String, storeID: String,
                          email: String, address: String, phoneNumber: String, country: String,
                          city: String, hasRecycling: Boolean, takesMobiles: Boolean,
                          takesComponents: Boolean, takesOther: Boolean, websiteURL: String) {
        var uid = firebaseAuth.currentUser?.uid
        while (uid == null) {
            //TODO: fix so it cant go into an infinite loop
            uid = firebaseAuth.currentUser?.uid
        }
        val about = ""
        val companyUser =
            CompanyUser(
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

        val user = hashMapOf(
            "companyName" to companyName,
            "about" to about,
            "userName" to userName,
            "storeID" to storeID,
            "email" to email,
            "address" to address,
            "phoneNumber" to phoneNumber,
            "country" to country,
            "city" to city,
            "hasRecycling" to hasRecycling,
            "hasImage" to false,
            "takesMobiles" to takesMobiles,
            "takesComponents" to takesComponents,
            "takesOther" to takesOther,
            "websiteURL" to websiteURL
        )
        Log.d(TAG, "createCompanyUser: $user")

        val db = FirebaseFirestore.getInstance(firebaseApplication)
        db.collection("CompanyUsers")
            .document("$uid")
            .set(user)
            .addOnSuccessListener {
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: $uid"
                )
                CompanyUserSingleton.getInstance.companyUser = companyUser
                userCreatedLiveData.postValue(true)
            }
            .addOnFailureListener { e -> Log.i(TAG, "Error adding document $e", e)
                userCreatedLiveData.postValue(false)}


    }

    fun emailVerification() {
        var user = firebaseAuth.currentUser
        while (user == null) {
            //TODO: fix so it cant go into an infinite loop
            user = firebaseAuth.currentUser
        }
        user.sendEmailVerification()
            .addOnCompleteListener (ContextCompat.getMainExecutor(application), { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                    emailSentLiveData.postValue(true)
                } else {
                    emailSentLiveData.postValue(false)
                }
            })
    }

}
