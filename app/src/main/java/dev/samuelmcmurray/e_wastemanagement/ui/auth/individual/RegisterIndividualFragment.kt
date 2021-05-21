package dev.samuelmcmurray.e_wastemanagement.ui.auth.individual

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseUser
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.databinding.RegisterIndividualFragmentBinding
import dev.samuelmcmurray.e_wastemanagement.ui.auth.LoginFragment
import dev.samuelmcmurray.e_wastemanagement.ui.auth.RegisterViewModel
import java.util.concurrent.Executors

private const val TAG = "RegisterIndividualFrag"

class RegisterIndividualFragment : Fragment() {
    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var binding: RegisterIndividualFragmentBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var calendarView: DatePicker
    private lateinit var userID: String
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.register_individual_fragment,
            container,
            false
        )
        binding.lifecycleOwner = this

        navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
        calendarView = binding.calendarView
        calendarView.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        val signUpButton = binding.buttonSignUp
        val cancelButton = binding.buttonCancel
        val dateOfBirth = binding.editTextDOB

        signUpButton.setOnClickListener {
            val firstName = binding.editTextFirstName.text.toString()
            val lastName = binding.editTextLastName.text.toString()
            val userName = binding.editTextUserName.text.toString()
            val email = binding.editTextEmailAddress.text.toString()
            val city = binding.editTextCity.text.toString()
            val country = binding.editTextCountry.text.toString()
            val password = binding.editTextPassword.text.toString()
            val passwordConfirm = binding.editTextPassword2.text.toString()
            val dob = dateOfBirth.text.toString()

            register(
                firstName, lastName, userName, email, city, country,
                password, passwordConfirm, dob
            )
            hideKeyboard()
        }

        cancelButton.setOnClickListener {
            navController.navigate(R.id.loginFragment)
        }

        dateOfBirth.setOnClickListener {
            showHide(calendarView)
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            calendarView.setOnDateChangedListener { view, year, month, dayOfMonth ->
                val theMonth = month + 1
                dateOfBirth.text = "$theMonth-$dayOfMonth-$year"
            }
        }
    }

    private fun register(
        firstName: String, lastName: String, userName: String, email: String, city: String,
        country: String, password: String, passwordConfirm: String, dob: String
    ) {
        if (firstName.isNotBlank() && lastName.isNotBlank() && userName.isNotBlank() &&
                email.isNotBlank() && city.isNotBlank() && country.isNotBlank() && password.isNotBlank()
                && passwordConfirm.isNotBlank() && (password == passwordConfirm) && dob.isNotBlank()) {
            val executor = Executors.newSingleThreadExecutor()
            executor.execute {
                viewModel.registerIndividualUser(
                    firstName,
                    lastName,
                    userName,
                    email,
                    city,
                    country,
                    password,
                    dob
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
                Log.d(TAG, "EmailSent: ")
            }
        })
    }

    private fun showHide(view: View) {
        if (view.visibility == View.GONE) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}