package dev.samuelmcmurray.e_wastemanagement.ui.shops
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.adapters.CompanyRecyclerViewAdapter
import dev.samuelmcmurray.e_wastemanagement.data.model.CompanyUser
import dev.samuelmcmurray.e_wastemanagement.utils.CompaniesCallBack
import dev.samuelmcmurray.e_wastemanagement.utils.StoreUtils

private const val TAG = "StoreFragment"
class ShopsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = ShopsFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.shops_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAll()
    }

    private fun loadAll() {
        StoreUtils.newInstance().readStoresFromFirebase(object: CompaniesCallBack {
            override fun onCallback(value: ArrayList<CompanyUser>) {
                setUpRecyclerView(value, requireView())
            }
        })
    }

    private fun setUpRecyclerView(companiesList: ArrayList<CompanyUser>, view: View) {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.shopsFrag)
        val recyclerViewAdapter =
            CompanyRecyclerViewAdapter(requireContext(), companiesList as List<CompanyUser>, view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text = parent?.getItemAtPosition(position)
        Log.d(TAG, "onItemSelected: $text")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}