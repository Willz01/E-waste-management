package dev.samuelmcmurray.e_wastemanagement.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.databinding.UserTypeFragmentBinding


class UserTypeFragment : Fragment() {
    companion object {
        fun newInstance() = UserTypeFragment()
    }

    private lateinit var binding: UserTypeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_type_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }
}