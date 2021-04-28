package dev.samuelmcmurray.e_wastemanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class UploadFragment : Fragment() {
    private lateinit var itemName: TextInputEditText
    private lateinit var itemImage: ImageView
    private lateinit var itemModel: TextInputEditText
    private lateinit var itemDescription: TextInputEditText


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

        itemName = requireView().findViewById(R.id.editTextTitle)
        itemDescription = requireView().findViewById(R.id.editTextDescription)
        itemImage = requireView().findViewById(R.id.imageUpload)
        itemModel = requireView().findViewById(R.id.editTextModel)

        requireView().findViewById<Button>(R.id.buttonUpload).setOnClickListener {
            val tempItem = Item(
                itemName.text.toString(),
                "currentUID",
                "xxID",
                itemModel.text.toString(),
                itemModel.text.toString(),
                itemImage.toString(),
                itemDescription.toString()
            )
            ItemUtils.newInstance().addItemToFirebase(tempItem)
            Snackbar.make(requireView(), "Item added!", Snackbar.LENGTH_SHORT).show()
        }

    }

}