package dev.samuelmcmurray.e_wastemanagement.ui.auth

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
import com.google.firebase.auth.FirebaseUser
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.databinding.RegisterCompanyFragmentBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_company_fragment, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        val signUpButton = binding.buttonSignUp
        val cancelButton = binding.buttonCancel


        signUpButton.setOnClickListener {
            val companyNameText = binding.editTextCompanyName
            val userNameText = binding.editTextUserName
            val storeIDText = binding.editTextStoreID
            val emailText = binding.editTextEmailAddress
            val phoneNumberText = binding.editTextPhoneNumber
            val cityText = binding.editTextCity
            val countryText = binding.editTextCountry
            val passwordText = binding.editTextPassword
            val passwordConfirmText = binding.editTextPassword2

            val companyName = companyNameText.text.toString()
            val userName = userNameText.text.toString()
            val storeID = storeIDText.text.toString()
            val email = emailText.text.toString()
            val phoneNumber = phoneNumberText.text.toString()
            val city = cityText.text.toString()
            val country = countryText.text.toString()
            val password = passwordText.text.toString()
            val passwordConfirm = passwordConfirmText.text.toString()

            register(companyName, userName, storeID, email, phoneNumber, country, city,
                password, passwordConfirm)
            hideKeyboard()
        }

        cancelButton.setOnClickListener {
            //nav to login
        }
    }

    private fun register(companyName: String, userName: String, storeID: String, email: String,
                         phoneNumber: String, country: String, city: String, password: String,
                         passwordConfirm: String) {
        if (companyName.isNotBlank() && storeID.isNotBlank() && userName.isNotBlank() &&
            email.isNotBlank() && city.isNotBlank() && phoneNumber.isNotBlank() && country.isNotBlank() &&
            password.isNotBlank() && passwordConfirm.isNotBlank() && (password == passwordConfirm)) {
            val executor = Executors.newSingleThreadExecutor()
            executor.execute {
                viewModel.registerCompanyUser(
                    companyName,
                    userName,
                    storeID,
                    email,
                    phoneNumber,
                    country,
                    city,
                    password
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