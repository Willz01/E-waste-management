package dev.samuelmcmurray.e_wastemanagement.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.adapters.ProfileAdapter
import dev.samuelmcmurray.e_wastemanagement.data.model.Item
import dev.samuelmcmurray.e_wastemanagement.data.singleton.IndividualUserSingleton
import dev.samuelmcmurray.e_wastemanagement.utils.ItemUtils
import dev.samuelmcmurray.e_wastemanagement.utils.ItemsCallback


class ProfileFragment : Fragment() {

    private lateinit var userName: TextView
    private lateinit var firstName: TextView
    private lateinit var secondName: TextView
    private lateinit var email: TextView
    private lateinit var dateOfBirth: TextView
    private lateinit var city: TextView
    private lateinit var country: TextView

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private var UUID = IndividualUserSingleton.getInstance.individualUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)


        userName = view.findViewById(R.id.userName)
        firstName = view.findViewById(R.id.firstName)
        secondName = view.findViewById(R.id.secondName)
        email = view.findViewById(R.id.email)
        dateOfBirth = view.findViewById(R.id.dob)
        city = view.findViewById(R.id.city)
        country = view.findViewById(R.id.country)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO : Will be null - actual user has to be parsed in from firebase
        // userName.text = IndividualUserSingleton.getInstance.individualUser?.userName
        firstName.text = IndividualUserSingleton.getInstance.individualUser?.firstName
        secondName.text = IndividualUserSingleton.getInstance.individualUser?.lastName
        email.text = IndividualUserSingleton.getInstance.individualUser?.email
        dateOfBirth.text = IndividualUserSingleton.getInstance.individualUser?.dob
        city.text = IndividualUserSingleton.getInstance.individualUser?.city
        country.text = IndividualUserSingleton.getInstance.individualUser?.country

        /**
         * TODO : Will be null - actual user ID has to be parsed in from firebase
         * USing "CurrentID" now to test
         */
        filterView("CurrentID", requireView())
    }


    private fun filterView(UID: String?, view: View) {

        val itemsToRemove = ArrayList<Item>()
        ItemUtils.newInstance().readItemsFromFirebase(object : ItemsCallback {
            override fun onCallback(value: ArrayList<Item>) {
                value.forEach { item: Item ->
                    if (item.userID != UID!!) {
                        itemsToRemove.add(item)
                    }
                }
                value.removeAll(itemsToRemove)
                val recyclerViewAdapter =
                    ProfileAdapter(requireContext(), value as List<Item>, view)
                recyclerViewAdapter.notifyDataSetChanged()
                setUpRecyclerView(value, view)
            }
        })
    }

    private fun setUpRecyclerView(itemsList: ArrayList<Item>, view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.uploaded_rv_profile)
        val recyclerViewAdapter =
            ProfileAdapter(requireContext(), itemsList as List<Item>, view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.adapter = recyclerViewAdapter
    }
}