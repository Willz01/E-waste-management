package dev.samuelmcmurray.e_wastemanagement.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import dev.samuelmcmurray.e_wastemanagement.data.model.Item
import dev.samuelmcmurray.e_wastemanagement.utils.ImageUriCallBack
import dev.samuelmcmurray.e_wastemanagement.utils.ItemUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "UploadRepository"

class UploadRepository(var context: Context) {


    private val imageRef = FirebaseStorage.getInstance().reference
    private var downloadUris = ArrayList<String>()
    private var trackerCount: Int = 0

    fun uploadImageToStorage(
        uploadedImagesUris: ArrayList<Uri>,
        uploadedImagesFiles: ArrayList<String>,
        itemName: String,
        userId: String,
        itemID: String, itemType: String,
        purchase: String,
        itemModel: String,
        itemDescription: String
    ) {
        try {
            trackerCount = uploadedImagesFiles.size
            for ((counter, file) in uploadedImagesUris.withIndex()) {
                file.let {
                    val image = imageRef.child("/itemImages/${uploadedImagesFiles[counter]}")
                    val uploadTask = image.putFile(it)
                    uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> {
                        return@Continuation image.downloadUrl
                    }).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUrl = task.result
                            if (downloadUrl != null) {
                                addUriToFirebase(downloadUrl.toString())
                                Log.d(TAG, "uploadImageToStorage: $downloadUrl")
                                downloadUris.add(downloadUrl.toString())
                                Log.d(TAG, "uploadImageToStorage: size ${downloadUris.size} ")
                                Log.d(TAG, "uploadImageToStorage: counter $counter")
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }

        retrieveUris(object : ImageUriCallBack {
            override fun onCallback(value: ArrayList<String>) {
                Log.d(TAG, "onCallback: ${value.size}")
                val tempItem = Item(
                    itemName,
                    userId,
                    itemID,
                    itemType,
                    purchase,
                    try {
                        value[0].toString()
                    } catch (e: Exception) {
                        null
                    },
                    try {
                        value[1].toString()
                    } catch (e: Exception) {
                        null
                    },
                    try {
                        value[2].toString()
                    } catch (e: Exception) {
                        null
                    },
                    try {
                        value[3].toString()
                    } catch (e: Exception) {
                        null
                    },
                    itemModel,
                    itemDescription
                )
                ItemUtils.newInstance().addItemToFirebase(tempItem)
                Log.d(TAG, "onCallback: Added")
            }

        })
    }


    @SuppressLint("SimpleDateFormat")
    private fun addUriToFirebase(uri: String) {
        val uriCollection = FirebaseFirestore.getInstance().collection("uris")
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val uriMap = hashMapOf("imageUri" to uri, "time" to timeStamp)
        uriCollection.add(uriMap)
    }

    /**
     * Retrieve lastly added uris according to number of files to be uploaded
     */
    private fun retrieveUris(imageUriCallBack: ImageUriCallBack) {
        Log.d(TAG, "retrieveUris: tracker $trackerCount")
        val uriCollection = FirebaseFirestore.getInstance().collection("uris")
            .orderBy("time", Query.Direction.DESCENDING).limit(trackerCount.toLong())
        uriCollection.get().addOnSuccessListener { uris ->
            if (uris.isEmpty) {
                Log.d(TAG, "retrieveUris: Error retrieving")
            } else {
                val listUri = ArrayList<String>()

                for (uri in uris) {
                    val uriStuff = uri["imageUri"]
                    listUri.add(uriStuff.toString())
                }
                Log.d(TAG, "retrieveUris: ${listUri.size}")

                imageUriCallBack.onCallback(listUri)
            }
            uris.documents.clear()
            trackerCount = 0
        }


    }


}