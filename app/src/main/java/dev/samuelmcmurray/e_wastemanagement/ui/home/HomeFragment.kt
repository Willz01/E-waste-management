package dev.samuelmcmurray.e_wastemanagement.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.radiobutton.MaterialRadioButton
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.adapters.RecyclerViewAdapter
import dev.samuelmcmurray.e_wastemanagement.data.model.Item
import dev.samuelmcmurray.e_wastemanagement.utils.ItemUtils
import dev.samuelmcmurray.e_wastemanagement.utils.ItemsCallback

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? =
            inflater.inflate(R.layout.home_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //    var name: String,
        //    var userID: String,
        //    var id: String,
        //    var type : String,
        //    var purchaseYear: String?,
        //    var image1: String?, var image2: String?, var image3: String?, var image4: String?,
        //    var model: String,
        //    var description: String

        // load items from firebase and fill to recycler view
        // var itemsListFromFirebase = ArrayList<Item>()
        ItemUtils.newInstance().readItemsFromFirebase(object : ItemsCallback {
            override fun onCallback(value: ArrayList<Item>) {
                setUpRecyclerView(value, view)
            }
        })
        // Log.d(TAG, "onViewCreated: ${itemsListFromFirebase.size} ")

        val phoneButton = view.findViewById<MaterialRadioButton>(R.id.phoneButton)
        val tabletButton = view.findViewById<MaterialRadioButton>(R.id.tabletButton)
        val laptopButton = view.findViewById<MaterialRadioButton>(R.id.laptopButton)

        phoneButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                filterItems("Phone/Laptop", view)
                phoneButton.isChecked = true

                tabletButton.isChecked = false
                laptopButton.isChecked = false
            }

        })

        tabletButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                filterItems("Computer part", view)
                tabletButton.isChecked = true

                phoneButton.isChecked = false
                laptopButton.isChecked = false
            }

        })

        laptopButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                filterItems("Other", view)
                laptopButton.isChecked = true

                tabletButton.isChecked = false
                phoneButton.isChecked = false
            }

        })


        super.onViewCreated(view, savedInstanceState)
    }


    private fun setUpRecyclerView(itemsList: ArrayList<Item>, view: View) {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.upload_rv)
        val recyclerViewAdapter = RecyclerViewAdapter(requireContext(), itemsList as List<Item>, view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun filterItems(itemType: String, view: View) {
        val itemsToRemove = ArrayList<Item>()
        ItemUtils.newInstance().readItemsFromFirebase(object : ItemsCallback {
            override fun onCallback(value: ArrayList<Item>) {
                value.forEach { item: Item ->
                    if (!item.type.contains(itemType)) {
                        itemsToRemove.add(item)
                    }
                }
                value.removeAll(itemsToRemove)
                val recyclerViewAdapter = RecyclerViewAdapter(requireContext(), value as List<Item>, view)
                recyclerViewAdapter.notifyDataSetChanged()
                setUpRecyclerView(value, view)
            }
        })
    }
}


