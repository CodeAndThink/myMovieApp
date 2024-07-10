package com.truong.movieapplication.ui.mainscreen.profile.options

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.set
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.truong.movieapplication.data.connections.local.UserDatabase
import com.truong.movieapplication.data.models.User
import com.truong.movieapplication.data.respository.FirebaseAuthService
import com.truong.movieapplication.data.respository.LoginRepository
import com.truong.movieapplication.data.respository.SharedReferencesHelper
import com.truong.movieapplication.databinding.FragmentInforComponentBinding
import com.truong.movieapplication.ui.login.LoginViewModel
import com.truong.movieapplication.ui.login.LoginViewModelFactory
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModel

class InforComponent : Fragment() {
    private lateinit var _binding: FragmentInforComponentBinding
    private val binding get() = _binding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInforComponentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = UserDatabase.getDatabase(requireContext()).userDao()
        val factory = LoginViewModelFactory(LoginRepository(FirebaseAuthService(), dao))
        loginViewModel = ViewModelProvider(requireActivity(), factory)[LoginViewModel::class.java]
        loginViewModel.user.observe(requireActivity()) {
            val user = it.getOrNull()
            if (user != null) {
                binding.emailBox.setText(user.email)
                binding.displayNameBox.setText(user.display_name)
                binding.dobBox.setText(user.dob)
                if (user.gender != null) {
                    when(user.gender) {
                        true -> {
                            binding.maleRadioButton.isChecked = true
                        }
                        false -> {
                            binding.femaleRadioButton.isChecked = true
                        }
                    }
                }
                binding.addressBox.setText(user.address)
                binding.phoneBox.setText(user.phone)
            }
        }

        binding.backBtn.setOnClickListener {
            parentFragmentManager
                .popBackStack()
        }

        binding.changeInformationButton.setOnClickListener {
            enableChangeInformationButton()
        }

        binding.changeInformationApplyButton.setOnClickListener {
            loginViewModel.updateUserData(
                binding.displayNameBox.text.toString(),
                binding.dobBox.text.toString(),
                if (binding.maleRadioButton.isChecked) true else false,
                binding.addressBox.text.toString(),
                binding.phoneBox.text.toString()
            )
            disableChangeInformationButton()
        }
    }

    private fun enableChangeInformationButton() {
        binding.displayNameBox.isEnabled = true
        binding.dobBox.isEnabled = true
        binding.maleRadioButton.isEnabled = true
        binding.femaleRadioButton.isEnabled = true
        binding.addressBox.isEnabled = true
        binding.phoneBox.isEnabled = true
        binding.changeInformationButton.visibility = View.GONE
        binding.changeInformationApplyButton.visibility = View.VISIBLE
    }

    private fun disableChangeInformationButton() {
        binding.displayNameBox.isEnabled = false
        binding.dobBox.isEnabled = false
        binding.maleRadioButton.isEnabled = false
        binding.femaleRadioButton.isEnabled = false
        binding.addressBox.isEnabled = false
        binding.phoneBox.isEnabled = false
        binding.changeInformationButton.visibility = View.VISIBLE
        binding.changeInformationApplyButton.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = InforComponent()
    }
}