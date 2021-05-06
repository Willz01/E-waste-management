package dev.samuelmcmurray.e_wastemanagement.ui.auth.company

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseUser
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.databinding.RegisterCompanyFragmentBinding
import dev.samuelmcmurray.e_wastemanagement.ui.auth.RegisterViewModel
import java.util.concurrent.Executors

private const val TAG = "RegisterCompanyFragment"
class RegisterCompanyFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterCompanyFragment()
    }

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: RegisterCompanyFragmentBinding
    private lateinit var userID : String
    private lateinit var user: FirebaseUser
    private lateinit var navHostFragment : NavHostFragment
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_company_fragment, container, false)
        binding.lifecycleOwner = this

        navHostFragment = requireActivity().supportFragmentManager.
        findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        val signUpButton = binding.buttonSignUp
        val cancelButton = binding.buttonCancel
        val yesButton = binding.yesRadioButton
        val noButton = binding.noRadioButton

        yesButton.setOnClickListener {
            noButton.isChecked = false
            yesButton.isChecked = true
        }

        noButton.setOnClickListener {
            yesButton.isChecked = false
            noButton.isChecked = true
        }

        signUpButton.setOnClickListener {
            val companyName = binding.editTextCompanyName.text.toString()
            val userName = binding.editTextUserName.text.toString()
            val storeID = binding.editTextStoreID.text.toString()
            val email = binding.editTextEmailAddress.text.toString()
            val address = binding.editTextAddress.text.toString()
            val phoneNumber = binding.editTextPhoneNumber.text.toString()
            val city = binding.editTextCity.text.toString()
            val country = binding.editTextCountry.text.toString()
            val password = binding.editTextPassword.text.toString()
            val passwordConfirm = binding.editTextPassword2.text.toString()
            val recycles: Boolean = yesButton.isChecked
            val websiteURL = binding.editTextWebsite.text.toString()
            val mobiles = binding.mobileRadio.isChecked
            val desktop = binding.desktopRadio.isChecked
            val other = binding.otherRadio.isChecked

            register(companyName, userName, storeID, email, address, phoneNumber, country, city,
                recycles, password, passwordConfirm, mobiles, desktop, other, websiteURL)
            hideKeyboard()
        }

        cancelButton.setOnClickListener {
            navController.navigate(R.id.action_registerCompanyFragment_to_userTypeFragment)
        }
    }

    private fun register(companyName: String, userName: String, storeID: String, email: String,
                         address: String, phoneNumber: String, country: String, city: String,
                         hasRecycling: Boolean, password: String, passwordConfirm: String,
                         takesMobiles: Boolean, takesComponents: Boolean, takesOther: Boolean,
                         websiteURL: String) {
        if (companyName.isNotBlank() && storeID.isNotBlank() && userName.isNotBlank() && address.isNotBlank()
            && email.isNotBlank() && city.isNotBlank() && phoneNumber.isNotBlank() && country.isNotBlank() &&
            password.isNotBlank() && passwordConfirm.isNotBlank() && (password == passwordConfirm) &&
                websiteURL.isNotBlank()) {
            val executor = Executors.newSingleThreadExecutor()
            executor.execute {
                viewModel.registerCompanyUser(
                    companyName,
                    userName,
                    storeID,
                    email,
                    address,
                    phoneNumber,
                    country,
                    city,
                    password,
                    hasRecycling,
                    takesMobiles,
                    takesComponents,
                    takesOther,
                    websiteURL
                )
            }
            viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
                val firebaseUser = it
                if (firebaseUser != null) {
                    user = firebaseUser
                    userID = user.uid
                    hideKeyboard()
                }
                Log.d(TAG, "login: " + firebaseUser.uid)
            })
        } else {
            if (password != passwordConfirm) {
                Toast.makeText(context, "Passwords must match", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, "All fields must be filled out", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        viewModel.userCreatedLiveData.observe(viewLifecycleOwner, Observer {
            val userCreated = it
            if (userCreated) {
                hideKeyboard()
                Log.d(TAG, "UserCreated: ")
            } else {
                Log.d(TAG, "UserCreated: failure")
            }
        })
        viewModel.emailSentLiveData.observe(viewLifecycleOwner, Observer {
            val emailSent = it
            if (emailSent) {
                hideKeyboard()
                navController.navigate(R.id.action_registerCompanyFragment_to_homeFragment)
                Log.d(TAG, "EmailSent: " )
            }
        })
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}