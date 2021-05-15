package dev.samuelmcmurray.e_wastemanagement.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.adapters.RecyclerViewAdapter
import dev.samuelmcmurray.e_wastemanagement.data.model.Item
import dev.samuelmcmurray.e_wastemanagement.utils.ItemUtils
import dev.samuelmcmurray.e_wastemanagement.utils.ItemsCallback

private const val TAG = "HomeFragment"

class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {

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

        loadAll()

        // spinner
        val spinner: Spinner = view.findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = this

        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadAll() {
        ItemUtils.newInstance().readItemsFromFirebase(object : ItemsCallback {
            override fun onCallback(value: ArrayList<Item>) {
                setUpRecyclerView(value, requireView())
            }
        })
    }


    private fun setUpRecyclerView(itemsList: ArrayList<Item>, view: View) {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.upload_rv)
        val recyclerViewAdapter =
            RecyclerViewAdapter(requireContext(), itemsList as List<Item>, view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun filterItems(itemType: String?, view: View) {
        if (itemType == "All") {
            loadAll()
        } else {
            val itemsToRemove = ArrayList<Item>()
            ItemUtils.newInstance().readItemsFromFirebase(object : ItemsCallback {
                override fun onCallback(value: ArrayList<Item>) {
                    value.forEach { item: Item ->
                        if (!item.type.contains(itemType!!)) {
                            itemsToRemove.add(item)
                        }
                    }
                    value.removeAll(itemsToRemove)
                    val recyclerViewAdapter =
                        RecyclerViewAdapter(requireContext(), value as List<Item>, view)
                    recyclerViewAdapter.notifyDataSetChanged()
                    setUpRecyclerView(value, view)
                }
            })
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text = parent?.getItemAtPosition(position)
        filterItems(text as String, requireView())
        Log.d(TAG, "onItemSelected: $text")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // TODO("Not yet implemented")
    }
}


