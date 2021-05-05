package dev.samuelmcmurray.e_wastemanagement.data.repository

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UploadRepository(var context: Context) {


    private val imageRef = FirebaseStorage.getInstance().reference


     fun uploadImageToStorage(
        uploadedImagesUris: ArrayList<Uri>,
        uploadedImagesFiles: ArrayList<String>
    ) = CoroutineScope(Dispatchers.IO).launch {
        try {
            var counter = 0;
            for (file in uploadedImagesUris) {
                file.let {
                    imageRef.child("/itemImages/${uploadedImagesFiles[counter]}").putFile(it)
                    counter++
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context, "Successfully uploaded image",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}