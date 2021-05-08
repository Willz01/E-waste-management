package dev.samuelmcmurray.e_wastemanagement

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.google.android.material.radiobutton.MaterialRadioButton

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

/*
        val testItem1 = Item(
            "Samsung A2",
            "zs11sa",
            "11231s",
            null,
            "Samsung",
            null,
            "Nice phone with camera issue"
        )

        val testItem2 = Item(
            "Samsung A3",
            "xxcsd34",
            "11231s",
            null,
            "Samsung",
            null,
            "Nice phone with camera charging issues"
        )

        ItemUtils.newInstance().addItemToFirebase(testItem1)
        ItemUtils.newInstance().addItemToFirebase(testItem2)

        */
        // load items from firebase and fill to recycler view
        // var itemsListFromFirebase = ArrayList<Item>()
        ItemUtils.newInstance().readItemsFromFirebase(object : ItemsCallback {
            override fun onCallback(value: ArrayList<Item>) {
                setUpRecyclerView(value)
            }
        })
        // Log.d(TAG, "onViewCreated: ${itemsListFromFirebase.size} ")

        val phoneButton = view.findViewById<MaterialRadioButton>(R.id.phoneButton)
        val tabletButton = view.findViewById<MaterialRadioButton>(R.id.tabletButton)
        val laptopButton = view.findViewById<MaterialRadioButton>(R.id.laptopButton)

        phoneButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                filterItems("phone")
                phoneButton.isChecked = true

                tabletButton.isChecked = false
                laptopButton.isChecked = false
            }

        })

        tabletButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                filterItems("tablet")
                tabletButton.isChecked = true

                phoneButton.isChecked = false
                laptopButton.isChecked = false
            }

        })

        laptopButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                filterItems("laptop")
                laptopButton.isChecked = true

                tabletButton.isChecked = false
                phoneButton.isChecked = false
            }

        })


        super.onViewCreated(view, savedInstanceState)
    }


    private fun setUpRecyclerView(itemsList: ArrayList<Item>) {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.upload_rv)
        val recyclerViewAdapter = RecyclerViewAdapter(requireContext(), itemsList as List<Item>)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun filterItems(itemType: String) {
        val itemsToRemove = ArrayList<Item>()
        ItemUtils.newInstance().readItemsFromFirebase(object : ItemsCallback {
            override fun onCallback(value: ArrayList<Item>) {
                value.forEach { item: Item ->
                    if (!item.name.contains(itemType) || !item.description.contains(itemType)) {
                        itemsToRemove.add(item)
                    }
                }
                value.removeAll(itemsToRemove)
                val recyclerViewAdapter = RecyclerViewAdapter(requireContext(), value as List<Item>)
                recyclerViewAdapter.notifyDataSetChanged()
                setUpRecyclerView(value)
            }
        })
    }
}


