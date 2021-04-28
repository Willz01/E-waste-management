package dev.samuelmcmurray.e_wastemanagement.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import dev.samuelmcmurray.e_wastemanagement.data.repository.RegisterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "RegisterViewModel"
class RegisterViewModel : AndroidViewModel {
    private var registerRepository : RegisterRepository
    private val myCoroutineScope = CoroutineScope(Dispatchers.IO)
    var userLiveData : MutableLiveData<FirebaseUser>
    var loggedOutLiveData : MutableLiveData<Boolean>
    var userCreatedLiveData: MutableLiveData<Boolean>
    var emailSentLiveData: MutableLiveData<Boolean>
    val channel = Channel<FirebaseUser>()

    constructor(application: Application) : super(application) {
        this.registerRepository = RegisterRepository(application)
        userLiveData = registerRepository.userLiveData
        loggedOutLiveData = registerRepository.loggedOutLiveData
        userCreatedLiveData = registerRepository.userCreatedLiveData
        emailSentLiveData = registerRepository.emailSentLiveData
    }

    fun registerIndividualUser(firstName: String, lastName: String, userName: String, email: String,
                 city: String, country: String, password: String, dob: String) {
        myCoroutineScope.launch {
            try {
                registerUser(email, password)
            } catch (e: Exception) {

            }
        }
        myCoroutineScope.launch {
            try {
                createIndividualUser(firstName, lastName, userName, email, city, country, dob)
            } catch(e: Exception) {
                Log.d(TAG, "register: $e")
            }
        }

        myCoroutineScope.launch {
            try {
                emailVerification()
            } catch(e: Exception) {
                Log.d(TAG, "register: $e")
            }
        }
    }

    fun registerCompanyUser(companyName: String, userName: String, storeID: String, email: String,
                            phoneNumber: String, country: String, city: String, password: String) {
        myCoroutineScope.launch {
            try {
                registerUser(email, password)
            } catch (e: Exception) {

            }
        }
        myCoroutineScope.launch {
            try {
                createCompanyUser(companyName, userName, storeID, email, phoneNumber, country, city)
            } catch(e: Exception) {
                Log.d(TAG, "register: $e")
            }
        }

        myCoroutineScope.launch {
            try {
                emailVerification()
            } catch(e: Exception) {
                Log.d(TAG, "register: $e")
            }
        }
    }

    private fun registerUser(email: String, password: String)  {
        registerRepository.registerEmail(email, password)
    }

    private suspend fun emailVerification() {
        delay(500)
        registerRepository.emailVerification()
    }

    private suspend fun createIndividualUser(firstName: String, lastName: String, userName: String,
                                             email: String, city: String, country: String, dob: String) {
        delay(500)
        registerRepository.createIndividualUser(firstName, lastName, userName, email, city, country, dob)
    }

    private suspend fun createCompanyUser(companyName: String, userName: String, storeID: String, email: String,
                                          phoneNumber: String, country: String, city: String) {
        delay(500)
        registerRepository.createCompanyUser(companyName, userName, storeID, email, phoneNumber, country, city)
    }
}
