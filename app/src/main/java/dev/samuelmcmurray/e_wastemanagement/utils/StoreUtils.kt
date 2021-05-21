package dev.samuelmcmurray.e_wastemanagement.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import dev.samuelmcmurray.e_wastemanagement.data.model.CompanyUser

private const val TAG = "StoreUtils"
class StoreUtils {

    companion object {
        fun newInstance() = StoreUtils()
    }

    fun readStoresFromFirebase(companiesCallBack: CompaniesCallBack): Boolean {
        val companiesCollection = FirebaseFirestore.getInstance().collection("CompanyUsers")
        var success = false
        companiesCollection.get().addOnSuccessListener { companies ->
            if (!companies.isEmpty) {
                val companiesList = ArrayList<CompanyUser>()
                for (company in companies) {
                    val userID = company["uid"].toString()
                    val companyName = company["companyName"].toString()
                    val about = company["about"].toString()
                    val userName = company["userName"].toString()
                    val storeID = company["storeID"].toString()
                    val email = company["email"].toString()
                    val address = company["address"].toString()
                    val phoneNumber = company["phoneNumber"].toString()
                    val country = company["country"].toString()
                    val city = company["city"].toString()
                    val hasRecycling = company["hasRecycling"].toString().toBoolean()
                    val hasImage = company["hasImage"].toString().toBoolean()
                    val takesMobiles = company["takesMobiles"].toString().toBoolean()
                    val takesComponents = company["takesComponents"].toString().toBoolean()
                    val takesOther = company["takesOther"].toString().toBoolean()
                    val websiteURL = company["websiteURL"].toString()
//                    val checkRating = company["rating"]

                    val imageURL = company["imageURL"].toString()

                    val companyUser = CompanyUser(
                        userID,
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
                    companyUser.rating = 0
                    if (hasImage) {
                      companyUser.imageURL = imageURL
                    }
                    companiesList.add(companyUser)
                }
                companiesCallBack.onCallback(companiesList)
                success = true
            } else {
                Log.d(TAG, "readStoresFromFirebase: Empty list")
            }
        }
        return success
    }
}