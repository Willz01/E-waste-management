package dev.samuelmcmurray.e_wastemanagement.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.adapters.RecyclerViewAdapter
import dev.samuelmcmurray.e_wastemanagement.data.model.Item
import dev.samuelmcmurray.e_wastemanagement.data.singleton.CompanyUserSingleton
import dev.samuelmcmurray.e_wastemanagement.data.singleton.IndividualUserSingleton
import dev.samuelmcmurray.e_wastemanagement.databinding.HomeFragmentBinding
import dev.samuelmcmurray.e_wastemanagement.utils.ItemUtils
import dev.samuelmcmurray.e_wastemanagement.utils.ItemsCallback

private const val TAG = "HomeFragment"

class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var progressBar: ProgressBar
    private var companyUser = false
    private var individualUser = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.lifecycleOwner = this
        progressBar = binding.progressBar2
        progressBar.visibility = View.GONE
        Log.d(TAG, "onCreateView: ${binding.root}")
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        if (CompanyUserSingleton.getInstance.companyUser == null &&
                IndividualUserSingleton.getInstance.individualUser == null) {
            getUser()
        }
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUser() {
        progressBar.visibility = View.VISIBLE
        progressBar.progress
        viewModel.getCompanyUser()
        viewModel.companyUser.observe(viewLifecycleOwner, {
            companyUser = it
            if (CompanyUserSingleton.getInstance.companyUser != null) {
                Log.d(TAG, "CompanyUser = $companyUser")
            }
        })

        viewModel.getIndividualUser()
        viewModel.individualUser.observe(viewLifecycleOwner, {
            individualUser = it
            if (IndividualUserSingleton.getInstance.individualUser != null) {
                Log.d(TAG, "IndividualUser = $individualUser ")
            }
        })

        progressBar.visibility = View.GONE
    }
}


