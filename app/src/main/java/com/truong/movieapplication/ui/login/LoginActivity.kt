package com.truong.movieapplication.ui.login

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.truong.movieapplication.R
import com.truong.movieapplication.data.connections.local.UserDatabase
import com.truong.movieapplication.data.respository.FirebaseService
import com.truong.movieapplication.data.respository.LoginRepository
import com.truong.movieapplication.data.respository.SharedReferencesHelper
import com.truong.movieapplication.databinding.ActivityLoginBinding
import com.truong.movieapplication.ui.login.login.LoginComponent

class LoginActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityLoginBinding
    private val binding get() = _binding
    private lateinit var authViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferencesHelper = SharedReferencesHelper(this)
        val dao = UserDatabase.getDatabase(this).userDao()
        val repository = LoginRepository(FirebaseService(), dao, preferencesHelper)
        val factory = LoginViewModelFactory(repository)
        authViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, LoginComponent())
            .commit()
    }
}