package dev.samuelmcmurray.e_wastemanagement.ui.auth

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        binding.lifecycleOwner = this


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        val buttonCancel = binding.buttonCancel
        val buttonReset = binding.buttonReset

        buttonCancel.setOnClickListener {
            //nav to login fragment
        }

        buttonReset.setOnClickListener {
            val emailText = binding.editTextEmailAddress
            val email = emailText.text.toString().trim()
            resetPassword(email)
        }
    }

    private fun resetPassword(email: String) {
        if (email.isNotBlank()) {
            viewModel.resetPassword(email)
            viewModel.resetEmailLiveData.observe(viewLifecycleOwner, Observer {
                val emailSent = it
                if (emailSent) {
                    hideKeyboard()
                    Toast.makeText(context, "Email sent", Toast.LENGTH_SHORT)
                        .show()
                    // nav to login fragment
                } else {
                    Toast.makeText(context, "Make sure email is correct", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } else {
            Toast.makeText(context, "Email must be entered", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}