package dev.samuelmcmurray.e_wastemanagement.ui.company

import android.Manifest
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.data.model.CompanyUser


private const val TAG = "CompanyFragment"
class CompanyFragment(val company: CompanyUser) : Fragment(), OnMapReadyCallback {

    private lateinit var viewModel: CompanyViewModel

    private lateinit var address: String
    private lateinit var latLng: LatLng

    private val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestPermission()) { success ->
        if (success) {
            Toast.makeText(requireContext(), "Permissions Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Permissions denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.company_fragment, container, false)
        val companyNameText = view.findViewById<TextView>(R.id.company_name)
        val imageView = view.findViewById<ImageView>(R.id.company_image)
        val addressText = view.findViewById<TextView>(R.id.address)
        val city = company.city
        val phoneNumberText = view.findViewById<TextView>(R.id.phoneNumber)
        val emailText = view.findViewById<TextView>(R.id.email_shop)
        val ratingBar = view.findViewById<RatingBar>(R.id.rating)
        val mapView = view.findViewById<MapView>(R.id.mapView)
        Log.d(TAG, "onCreateView: ${company}")

        companyNameText.text = company.companyName
        address = company.address + ", " + city
        addressText.text = address
        phoneNumberText.text = company.phoneNumber
        emailText.text = company.email
        ratingBar.numStars = company.rating.toInt()

        try {
            if (company.hasImage) {
                Glide.with(requireContext()).load(company.imageURL).into(imageView)
            }
        } catch (exception: Exception) {
            Log.d(TAG, "onCreateView: Failed possibly no picture")
        }
        requestPermissions.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        mapView.getMapAsync(this)

        return view
    }


    override fun onMapReady(map: GoogleMap) {
        val geoCoder = Geocoder(requireContext())
        val newAddress = geoCoder.getFromLocationName(address,3)
        if (newAddress != null) {
            val location: Address = newAddress[0]
            val latitude = location.latitude
            val longitude = location.longitude
            latLng = LatLng(latitude, longitude)
        }

        map.addMarker(MarkerOptions().position(latLng).title(company.companyName))
    }


}