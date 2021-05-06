package dev.samuelmcmurray.e_wastemanagement.ui.auth

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.databinding.LoginFragmentBinding

private const val TAG = "LoginFragment"
class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, R.layout.login_fragment, container, false)
        binding.lifecycleOwner = this

        navHostFragment = requireActivity().supportFragmentManager.
        findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val loginButton = binding.buttonLogin
        val registerText = binding.textViewSignUp
        val forgotPasswordText = binding.textViewForgotPassword

        loginButton.setOnClickListener {
            val editTextEmail = binding.editTextEmailAddress
            val editTextPassword = binding.editTextPassword

            val email: String = editTextEmail.text.toString().trim()
            val password: String = editTextPassword.text.toString()
            login(email, password)
        }

        registerText.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_userTypeFragment)
        }

        forgotPasswordText.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    private fun login(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            viewModel.login(email, password)
            viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
                val fireBaseUser = it
                if (fireBaseUser != null) {
                    hideKeyboard()
                }
                Log.d(TAG, "login: $fireBaseUser ")
            })
        } else {
            Toast.makeText(context, "Email and Password must be entered", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}