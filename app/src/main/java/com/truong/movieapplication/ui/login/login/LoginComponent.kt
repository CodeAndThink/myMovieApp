package com.truong.movieapplication.ui.login.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.truong.movieapplication.R
import com.truong.movieapplication.data.connections.local.UserDatabase
import com.truong.movieapplication.data.respository.FirebaseService
import com.truong.movieapplication.data.respository.LoginRepository
import com.truong.movieapplication.data.respository.SharedReferencesHelper
import com.truong.movieapplication.databinding.FragmentLoginComponentBinding
import com.truong.movieapplication.ui.login.LoginViewModel
import com.truong.movieapplication.ui.login.LoginViewModelFactory
import com.truong.movieapplication.ui.login.forgotpassword.ForgotComponent
import com.truong.movieapplication.ui.login.register.RegisterComponent
import com.truong.movieapplication.ui.mainscreen.MainActivity

class LoginComponent : Fragment() {
    private lateinit var _binding : FragmentLoginComponentBinding
    private val binding get() = _binding
    private lateinit var authViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginComponentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferencesHelper = SharedReferencesHelper(requireContext())
        val dao = UserDatabase.getDatabase(requireContext()).userDao()
        val repository = LoginRepository(FirebaseService(), dao, preferencesHelper)
        val factory = LoginViewModelFactory(repository)
        authViewModel = ViewModelProvider(requireActivity(), factory)[LoginViewModel::class.java]

        binding.createAccountBtn.setOnClickListener {
            val fragment = RegisterComponent.newInstance()
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

        binding.forgotPasswordLabel.setOnClickListener {
            val fragment = ForgotComponent.newInstance()
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

        binding.loginBtn.setOnClickListener {
            authViewModel.login(binding.inputEmail.text.toString(), binding.inputPassword.text.toString())
            authViewModel.loginResult.observe(viewLifecycleOwner) { result ->
                if (result) {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.putExtra("email", binding.inputEmail.text.toString())
                    intent.putExtra("password", binding.inputPassword.text.toString())
                    startActivity(intent)
                } else {
                    authViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        authViewModel.errorMessage.observe(requireActivity()) { message ->
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginComponent()
    }
}