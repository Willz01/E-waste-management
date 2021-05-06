package dev.samuelmcmurray.e_wastemanagement.ui.itemDetail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.data.model.Item

class ItemFragment(var item: Item) : Fragment() {

    private lateinit var itemName: TextView
    private lateinit var itemModel: TextView
    private lateinit var itemDescription: TextView
    private lateinit var itemType: TextView

    private lateinit var itemImage1: ImageView
    private lateinit var itemImage2: ImageView
    private lateinit var itemImage3: ImageView
    private lateinit var itemImage4: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_item, container, false)
        itemName = view.findViewById(R.id.item_name)
        itemDescription = view.findViewById(R.id.item_description)
        itemType = view.findViewById(R.id.item_type)
        itemModel = view.findViewById(R.id.item_model)

        itemImage1 = view.findViewById(R.id.image1)
        itemImage2 = view.findViewById(R.id.image2)
        itemImage3 = view.findViewById(R.id.image3)
        itemImage4 = view.findViewById(R.id.image4)

        itemName.text = item.name
        itemModel.text = item.model
        itemType.text = item.type
        itemDescription.text = item.description

        // images

        try {
            itemImage1.setImageURI(Uri.parse(item.image1))
        } catch (e: Exception) {
            itemImage1.visibility = View.GONE
        }

        try {
            itemImage2.setImageURI(Uri.parse(item.image2))
        } catch (e: Exception) {
            itemImage2.visibility = View.GONE
        }

        try {
            itemImage3.setImageURI(Uri.parse(item.image3))
        } catch (e: Exception) {
            itemImage3.visibility = View.GONE
        }

        try {
            itemImage4.setImageURI(Uri.parse(item.image4))
        } catch (e: Exception) {
            itemImage4.visibility = View.GONE
        }

        return view
    }

}