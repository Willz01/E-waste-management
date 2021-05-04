package dev.samuelmcmurray.e_wastemanagement.ui.upload

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.data.model.Item
import dev.samuelmcmurray.e_wastemanagement.utils.ItemUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val UPLOAD_REQUEST_CODE = 2
private const val REQUEST_IMAGE_CAPTURE = 3

private const val TAG = "UploadFragment"

class UploadFragment : Fragment() {
    private lateinit var itemName: TextInputEditText
    private lateinit var itemImage: ImageView
    private lateinit var itemModel: TextInputEditText
    private lateinit var itemDescription: TextInputEditText

    private lateinit var auth: FirebaseAuth

    private lateinit var currentPhotoPath: String


    private lateinit var addButton: ImageButton
    private lateinit var uploadButton: ImageButton
    private lateinit var imageView: ImageView

    private var imageUri: Uri? = null

    companion object {
        fun newInstance() = UploadFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.upload_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // auth = FirebaseAuth.getInstance()

        itemName = requireView().findViewById(R.id.editTextTitle)
        itemDescription = requireView().findViewById(R.id.editTextDescription)
        itemImage = requireView().findViewById(R.id.imageUpload)
        itemModel = requireView().findViewById(R.id.editTextModel)

        requireView().findViewById<Button>(R.id.buttonUpload).setOnClickListener {
            val tempItem = Item(
                itemName.text.toString(),
                "currentUID",
                "xxID",
                "Not used for now",
                itemModel.text.toString(),
                "No image yet",
                itemDescription.text.toString()
            )
            ItemUtils.newInstance().addItemToFirebase(tempItem)
            Snackbar.make(requireView(), "Item added!", Snackbar.LENGTH_SHORT).show()
        }



        addButton = view.findViewById(R.id.addButton)
        uploadButton = view.findViewById(R.id.uploadButton)
        imageView = view.findViewById(R.id.imageUpload)


        // upload image from default gallery application
        uploadButton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, UPLOAD_REQUEST_CODE)
        }

        // snap new image
        addButton.setOnClickListener {
            if (requireContext().packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                dispatchTakePictureIntent()
            } else {
                Snackbar.make(requireView(), "No camera available", Snackbar.LENGTH_SHORT).show()
            }

        }


    }

    private fun dispatchTakePictureIntent() = CoroutineScope(Dispatchers.IO).launch {

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Snackbar.make(requireView(), "Error taking photo", Snackbar.LENGTH_SHORT)
                .show()
        }
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            requireContext().let { it ->
                takePictureIntent.resolveActivity(it.packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        Snackbar.make(requireView(), "Error taking photo", Snackbar.LENGTH_SHORT)
                            .show()
                        null
                    }
                    Log.d(TAG, "dispatchTakePictureIntent: image created")
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            "com.example.android.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UPLOAD_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            imageView.setImageURI(imageUri)
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            val imageUri = data?.data

            val imageBitmap: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                imageUri?.let {
                    ImageDecoder.createSource(
                        requireContext().contentResolver,
                        it,
                    )
                }?.let {
                    ImageDecoder.decodeBitmap(
                        it,
                    )
                }
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
            }

            imageView.setImageBitmap(imageBitmap)

        } else {
            Log.d(TAG, "onActivityResult: Data is null")
        }
    }


    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        Log.d(TAG, "createImageFile: to create")
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            Log.d(TAG, "createImageFile: ${currentPhotoPath.toString()}")
        }
    }

}