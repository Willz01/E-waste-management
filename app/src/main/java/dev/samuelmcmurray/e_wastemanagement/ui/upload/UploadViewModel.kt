package dev.samuelmcmurray.e_wastemanagement.ui.upload

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.samuelmcmurray.e_wastemanagement.data.repository.UploadRepository
import kotlinx.coroutines.launch

class UploadViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UploadRepository = UploadRepository(application.applicationContext)

    fun addItem(
        uploadedImagesUris: ArrayList<Uri>,
        uploadedFiles: ArrayList<String>,
        itemName: String,
        userID: String,
        itemId: String, itemType: String,
        purchaseYear: String,
        itemModel: String,
        itemDescription: String
    ) {
        viewModelScope.launch {
            repository.uploadImageToStorage(
                uploadedImagesUris,
                uploadedFiles,
                itemName,
                userID,
                itemId,
                itemType,
                purchaseYear,
                itemModel,
                itemDescription
            )
        }
    }

}