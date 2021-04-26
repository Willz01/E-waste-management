package dev.samuelmcmurray.e_wastemanagement

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "HomeFragment"
class HomeFragment : Fragment(){
    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.home_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // load items from firebase and fill to recycler view
        // var itemsListFromFirebase = ArrayList<Item>()
        ItemUtils.newInstance().readItemsFromFirebase(object : ItemsCallback {
            override fun onCallback(value: ArrayList<Item>) {
                setUpRecyclerView(value)
            }
        })
        // Log.d(TAG, "onViewCreated: ${itemsListFromFirebase.size} ")


        super.onViewCreated(view, savedInstanceState)
    }



    private fun setUpRecyclerView(itemsList: ArrayList<Item>) {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.upload_rv)
        val recyclerViewAdapter = RecyclerViewAdapter(requireContext(), itemsList as List<Item>)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = recyclerViewAdapter
    }

}