package dev.samuelmcmurray.e_wastemanagement.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.databinding.WelcomeFragmentBinding


private const val TAG = "WelcomeFragment"
class WelcomeFragment : Fragment() {
    private lateinit var binding: WelcomeFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, R.layout.welcome_fragment, container, false)
        binding.lifecycleOwner = this

        val progressBar = binding.progressBar
        progressBar.progress
        return binding.root
    }
}
