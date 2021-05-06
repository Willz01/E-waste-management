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

        if (item.image1.isNullOrEmpty()){
            itemImage1.visibility = View.GONE
        }else{
            Glide.with(requireContext()).load(Uri.parse(item.image1)).into(itemImage1)
        }

        if (item.image2.isNullOrEmpty()){
            itemImage2.visibility = View.GONE
        }else{
            Glide.with(requireContext()).load(Uri.parse(item.image2)).into(itemImage2)
        }

        if (item.image3.isNullOrEmpty()){
            itemImage3.visibility = View.GONE
        }else{
            Glide.with(requireContext()).load(Uri.parse(item.image3)).into(itemImage3)
        }

        if (item.image4.isNullOrEmpty()){
            itemImage4.visibility = View.GONE
        }else{
            Glide.with(requireContext()).load(Uri.parse(item.image4)).into(itemImage4)
        }



        return view
    }

}