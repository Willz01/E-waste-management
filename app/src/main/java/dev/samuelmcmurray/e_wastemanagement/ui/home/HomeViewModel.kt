package dev.samuelmcmurray.e_wastemanagement.ui.home

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.samuelmcmurray.e_wastemanagement.data.repository.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "HomeViewModel"
class HomeViewModel: AndroidViewModel {
    private var homeRepository: HomeRepository
    var companyUser: MutableLiveData<Boolean>
    var individualUser: MutableLiveData<Boolean>
    private val myCoroutineScope = CoroutineScope(Dispatchers.IO)

    constructor(application: Application): super(application) {
        this.homeRepository = HomeRepository(application)
        this.companyUser = homeRepository.companyUserLiveData
        this.individualUser = homeRepository.individualUserLiveData
    }

    fun getCompanyUser() {
        try {
            myCoroutineScope.launch {
                homeRepository.getCompanyUser()
            }
        } catch (exception: Exception) {
            Log.d(TAG, "getCompanyUser: ${exception.message}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getIndividualUser() {
        try {
            myCoroutineScope.launch {
                homeRepository.getIndividualUser()
            }

        } catch (exception: Exception) {
            Log.d(TAG, "getIndividualUser: ${exception.message}")
        }
    }
}