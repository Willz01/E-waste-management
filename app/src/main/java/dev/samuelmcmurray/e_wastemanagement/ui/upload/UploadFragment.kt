package dev.samuelmcmurray.e_wastemanagement.ui.upload

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
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
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import dev.samuelmcmurray.e_wastemanagement.BuildConfig
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.adapters.ImageAdapter
import dev.samuelmcmurray.e_wastemanagement.data.model.Item
import dev.samuelmcmurray.e_wastemanagement.data.repository.UploadRepository
import dev.samuelmcmurray.e_wastemanagement.utils.ItemUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val UPLOAD_REQUEST_CODE = 2
private const val REQUEST_IMAGE_CAPTURE = 3

private const val TAG = "UploadFragment"

class UploadFragment : Fragment() {
    private lateinit var itemName: TextInputEditText
    private lateinit var itemModel: TextInputEditText
    private lateinit var itemDescription: TextInputEditText
    private lateinit var itemTypeRadio: RadioGroup

    private lateinit var auth: FirebaseAuth

    private lateinit var currentPhotoPath: String


    private lateinit var addButton: ImageButton
    private lateinit var uploadButton: ImageButton
    private lateinit var imageView: ImageView

    private lateinit var uploadImagesRecyclerView: RecyclerView

    private lateinit var imageUri: Uri

    // limited to 4 images
    private var uploadedImagesUris = ArrayList<Uri>(4)
    private var uploadedImagesFiles = ArrayList<String>(4)

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


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // auth = FirebaseAuth.getInstance()

        itemName = requireView().findViewById(R.id.editTextTitle)
        itemDescription = requireView().findViewById(R.id.editTextDescription)
        itemModel = requireView().findViewById(R.id.editTextModel)
        itemTypeRadio = requireView().findViewById(R.id.radioGroup)
        uploadImagesRecyclerView = requireView().findViewById<RecyclerView>(R.id.uploaded_images)

        val uploadRepository = UploadRepository(requireContext())

        requireView().findViewById<Button>(R.id.buttonUpload).setOnClickListener {
            when (checkNull()) {
                true -> {
                    val selectedTypeId = itemTypeRadio.checkedRadioButtonId
                    val typeString =
                        requireView().findViewById<RadioButton>(selectedTypeId).text.toString()
                    val tempItem = Item(
                        itemName.text.toString(),
                        "currentUID",
                        UUID.randomUUID().toString(),
                        type =  typeString,
                        "Not used for now",
                        try {
                            uploadedImagesUris[0].toString()
                        } catch (e: IndexOutOfBoundsException) {
                            null
                        },
                        try {
                            uploadedImagesUris[1].toString()
                        } catch (e: IndexOutOfBoundsException) {
                            null
                        },
                        try {
                            uploadedImagesUris[2].toString()
                        } catch (e: IndexOutOfBoundsException) {
                            null
                        },
                        try {
                            uploadedImagesUris[3].toString()
                        } catch (e: IndexOutOfBoundsException) {
                            null
                        },
                        itemModel.text.toString(),
                        itemDescription.text.toString()
                    )
                    ItemUtils.newInstance().addItemToFirebase(tempItem)
                    uploadRepository.uploadImageToStorage(uploadedImagesUris, uploadedImagesFiles)
                    Snackbar.make(requireView(), "Item added!", Snackbar.LENGTH_SHORT).show()

                    // clear fields
                    itemName.text = null
                    itemModel.text = null
                    itemDescription.text = null
                    itemTypeRadio.clearCheck()

                    imageView.visibility = View.VISIBLE
                    uploadImagesRecyclerView.visibility = View.GONE
                    requireView().findViewById<TextView>(R.id.textView3).text = "Add existing image"
                }
            }

        }

        addButton = view.findViewById(R.id.addButton)
        uploadButton = view.findViewById(R.id.uploadButton)
        imageView = view.findViewById(R.id.imageUpload)


        // upload image from default gallery application
        uploadButton.setOnClickListener {
            if (uploadedImagesUris.size <= 4) {
                val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
                galleryIntent.type = "image/*"
                startActivityForResult(galleryIntent, UPLOAD_REQUEST_CODE)
            } else {
                Snackbar.make(
                    requireView(),
                    "Maximum number of images exceeded.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        }

        // snap new image
        addButton.setOnClickListener {
            if (uploadedImagesUris.size <= 4) {
                requestCameraPermission.launch(Manifest.permission.CAMERA)
            } else {
                Snackbar.make(
                    requireView(),
                    "Maximum number of images exceeded.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        }


    }

    /**
     * returns false : fail
     * returns true : Good
     */
    private fun checkNull(): Boolean {
        if (itemTypeRadio.checkedRadioButtonId != -1) {
            return if (itemName.text.isNullOrEmpty() || itemDescription.text.isNullOrEmpty() || itemModel.text.isNullOrEmpty()) {
                Snackbar.make(
                    requireView(),
                    "Please fill all required fields",
                    Snackbar.LENGTH_SHORT
                ).show()
                false
            } else
                true
        } else
            Snackbar.make(
                requireView(),
                "Please select an item type",
                Snackbar.LENGTH_SHORT
            ).show()
        return false

    }

    private fun recyclerDisplay() {
        imageView.visibility = View.GONE
        uploadImagesRecyclerView.visibility = View.VISIBLE
        val imageAdapter = ImageAdapter(uploadedImagesUris, requireContext())
        uploadImagesRecyclerView.apply {
            adapter = imageAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                //uploadedImagesUris.add(imageUri)
                recyclerDisplay()
                // imageView.setImageURI(imageUri)
            } else {
                Toast.makeText(requireContext(), "Photo failed", Toast.LENGTH_SHORT).show()
            }
        }


    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { success ->
            if (success) {
                imageUri = getCameraCacheUri();
                uploadedImagesUris.add(imageUri)
                takePicture.launch(imageUri)
            } else {
                Toast.makeText(requireContext(), "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun getCameraCacheUri(): Uri {
        val file = createImageFile()
        Log.d(TAG, "getCameraCacheUri: ${file.canonicalFile}")
        uploadedImagesFiles.add(file.canonicalFile.toString().split("/")[9])
        imageUri = FileProvider.getUriForFile(
            requireContext(),
            BuildConfig.APPLICATION_ID + ".provider",
            file
        )
        return imageUri
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


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UPLOAD_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data!!
            /**
             * This will depend how we want to handle it
             * 1 - we can make each image in the rv have a remove button
             * 2 - or entirely clear the rv list, but in some way. - the way done below will clear the list
             * every time the button is clicked leaving the rv with only on image.
             */
            // uploadedImagesUris.clear()
            Log.d(TAG, "onActivityResult: ${imageUri.toString()}")
            uploadedImagesUris.add(imageUri)
            uploadedImagesFiles.add(File(imageUri.lastPathSegment!!).toString())
            requireView().findViewById<TextView>(R.id.textView3).text = "Add more images"
            recyclerDisplay()
            // imageView.setImageURI(imageUri)
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            val imageUri = data?.data

            val bitmap = when {
                Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                    requireContext().contentResolver,
                    imageUri
                )
                else -> {
                    val source = imageUri?.let {
                        ImageDecoder.createSource(
                            requireContext().contentResolver,
                            it
                        )
                    }
                    source?.let { ImageDecoder.decodeBitmap(it) }
                }
            }
            Log.d(TAG, "onActivityResult: ${imageUri.toString()}")
            imageView.setImageBitmap(bitmap)

        } else {
            Log.d(TAG, "onActivityResult: Data is null")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(requireView(), "Camera permission granted", Snackbar.LENGTH_LONG)
                    .show();
            }
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
            Log.d(TAG, "createImageFile: ${currentPhotoPath}")
        }
    }
}