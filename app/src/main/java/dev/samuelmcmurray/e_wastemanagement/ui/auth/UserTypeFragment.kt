package dev.samuelmcmurray.e_wastemanagement.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.databinding.UserTypeFragmentBinding


class UserTypeFragment : Fragment() {
    companion object {
        fun newInstance() = UserTypeFragment()
    }

    private lateinit var binding: UserTypeFragmentBinding
    private lateinit var navHostFragment : NavHostFragment
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_type_fragment, container, false)
        binding.lifecycleOwner = this

        navHostFragment = requireActivity().supportFragmentManager.
        findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        val individualRadio = binding.individualRadioButton
        val companyRadio = binding.companyRadioButton
        val signupButton = binding.buttonRegister
        val cancelButton = binding.buttonCancel

        individualRadio.setOnClickListener {
            individualRadio.isChecked = true
            companyRadio.isChecked = false
        }

        companyRadio.setOnClickListener {
            individualRadio.isChecked = false
            companyRadio.isChecked = true
        }

        signupButton.setOnClickListener {
            signup(individualRadio.isChecked)
        }

        cancelButton.setOnClickListener {
            navController.navigate(R.id.action_userTypeFragment_to_loginFragment)
        }

        return binding.root
    }

    private fun signup(isIndividual: Boolean) {
        if (isIndividual) {
            navController.navigate(R.id.action_userTypeFragment_to_registerIndividualFragment)
        } else {
            navController.navigate(R.id.action_userTypeFragment_to_registerCompanyFragment)
        }
    }
}