package dev.samuelmcmurray.e_wastemanagement.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import dev.samuelmcmurray.e_wastemanagement.data.repository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : AndroidViewModel {
    private var loginRepository : LoginRepository
    var userLiveData : MutableLiveData<FirebaseUser>
    var loggedOutLiveData : MutableLiveData<Boolean>
    var resetEmailLiveData: MutableLiveData<Boolean>
    private val myCoroutineScope = CoroutineScope(Dispatchers.IO)

    constructor(application: Application) : super(application) {
        loginRepository = LoginRepository(application)
        userLiveData = loginRepository.userLiveData
        loggedOutLiveData = loginRepository.loggedOutLiveData
        resetEmailLiveData = loginRepository.resetEmailLiveData
    }

    fun login(email : String, password : String)  {
        myCoroutineScope.launch {
            try {
                loginRepository.login(email, password)
            } catch (e : Exception) {

            }
        }
    }

    fun resetPassword(email: String) {
        myCoroutineScope.launch {
            try {
                loginRepository.resetPassword(email)
            } catch (e: Exception) {

            }
        }
    }
}