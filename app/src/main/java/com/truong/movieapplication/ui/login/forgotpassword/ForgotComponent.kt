package com.truong.movieapplication.ui.login.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.truong.movieapplication.R
import com.truong.movieapplication.data.connections.local.UserDatabase
import com.truong.movieapplication.data.respository.FirebaseAuthService
import com.truong.movieapplication.data.respository.LoginRepository
import com.truong.movieapplication.data.respository.SharedReferencesHelper
import com.truong.movieapplication.databinding.FragmentForgotComponentBinding
import com.truong.movieapplication.ui.login.LoginViewModel
import com.truong.movieapplication.ui.login.LoginViewModelFactory
import com.truong.movieapplication.ui.login.login.LoginComponent

class ForgotComponent : Fragment() {
    private lateinit var _binding: FragmentForgotComponentBinding
    private val binding get() = _binding
    private lateinit var authViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotComponentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferencesHelper = SharedReferencesHelper(requireContext())
        val dao = UserDatabase.getDatabase(requireContext()).userDao()
        val repository = LoginRepository(FirebaseAuthService(), dao, preferencesHelper)
        val factory = LoginViewModelFactory(repository)
        authViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        binding.returnLoginBtn.setOnClickListener {
            val fragment = LoginComponent.newInstance()
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

        binding.sendBtn.setOnClickListener {
            authViewModel.checkTimeExceeded() // Check if 5 minutes have passed
        }

        authViewModel.isTimeExceeded.observe(viewLifecycleOwner) { isExceeded ->
            if (isExceeded) {
                authViewModel.stopCountdown()
                Toast.makeText(requireContext(), "Wait 5 minutes to try again", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Check the email", Toast.LENGTH_SHORT).show()
                authViewModel.saveCurrentTime()
                authViewModel.startCountdown()
            }
        }

        authViewModel.timeLeft.observe(viewLifecycleOwner) { timeLeft ->
            binding.counterClockLabel.text = timeLeft.toString()
        }

        authViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ForgotComponent()
    }
}