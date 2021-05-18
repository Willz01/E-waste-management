package dev.samuelmcmurray.e_wastemanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.samuelmcmurray.e_wastemanagement.adapters.CompanyBidsAdapter
import dev.samuelmcmurray.e_wastemanagement.adapters.ProfileAdapter
import dev.samuelmcmurray.e_wastemanagement.data.model.Item
import dev.samuelmcmurray.e_wastemanagement.data.singleton.IndividualUserSingleton
import dev.samuelmcmurray.e_wastemanagement.utils.ItemUtils
import dev.samuelmcmurray.e_wastemanagement.utils.ItemsCallback

class CompanyBidsFragment : Fragment() {

    lateinit var textView: TextView
    private var UUID = IndividualUserSingleton.getInstance.individualUser?.uid


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company_bids, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.text_holder)
        if (IndividualUserSingleton.getInstance.individualUser?.userName != null) {
            textView.visibility = View.VISIBLE
            UUID?.let { getBids(it) }
        } else {
            textView.visibility = View.VISIBLE
            textView.text = getString(R.string.companyUserBoomer)
        }

    }

    companion object {
        fun newInstance() = CompanyBidsFragment()
    }

    private fun getBids(UID: String) {
        val itemsToRemove = ArrayList<Item>()
        ItemUtils.newInstance().readBids(object : ItemsCallback {
            override fun onCallback(value: ArrayList<Item>) {
                value.forEach { item: Item ->
                    if (item.userID != UID) {
                        itemsToRemove.add(item)
                    }
                }
                value.removeAll(itemsToRemove)
                if (value.isEmpty()) {
                    requireView().findViewById<RecyclerView>(R.id.bided_Items).visibility =
                        View.GONE
                    textView.visibility = View.VISIBLE
                } else {
                    textView.visibility = View.GONE
                    val recyclerViewAdapter =
                        CompanyBidsAdapter(requireContext(), value as List<Item>)
                    recyclerViewAdapter.notifyDataSetChanged()
                    setUpRecyclerView(value, requireView())
                }

            }
        })
    }

    private fun setUpRecyclerView(itemsList: ArrayList<Item>, view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.bided_Items)
        val recyclerViewAdapter =
            CompanyBidsAdapter(requireContext(), itemsList as List<Item>)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.adapter = recyclerViewAdapter
    }
}