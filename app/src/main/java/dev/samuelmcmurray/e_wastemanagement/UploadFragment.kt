package dev.samuelmcmurray.e_wastemanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class UploadFragment : Fragment() {
    companion object {
        fun newInstance() = UploadFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.upload_fragment, container, false)

}