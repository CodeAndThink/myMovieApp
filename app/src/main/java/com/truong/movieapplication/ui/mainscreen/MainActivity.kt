package com.truong.movieapplication.ui.mainscreen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.truong.movieapplication.R
import com.truong.movieapplication.data.connections.local.UserDatabase
import com.truong.movieapplication.data.connections.network.ApiClients
import com.truong.movieapplication.data.respository.FirebaseAuthService
import com.truong.movieapplication.data.respository.LoginRepository
import com.truong.movieapplication.data.respository.MovieRepository
import com.truong.movieapplication.data.respository.SharedReferencesHelper
import com.truong.movieapplication.databinding.ActivityMainBinding
import com.truong.movieapplication.ui.login.LoginViewModel
import com.truong.movieapplication.ui.login.LoginViewModelFactory
import com.truong.movieapplication.ui.mainscreen.home.HomeComponent
import com.truong.movieapplication.ui.mainscreen.notification.NotificationComponent
import com.truong.movieapplication.ui.mainscreen.profile.ProfileComponent
import com.truong.movieapplication.ui.mainscreen.search.SearchComponent
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModel
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding
    private lateinit var email: String
    private lateinit var password: String
    private val apiService = ApiClients.dataInstance
    private val repository = MovieRepository(apiService)

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginViewModelFactory: LoginViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferencesHelper = SharedReferencesHelper(this)
        val dao = UserDatabase.getDatabase(this).userDao()
        val loginRepository = LoginRepository(FirebaseAuthService(), dao, preferencesHelper)
        loginViewModelFactory = LoginViewModelFactory(loginRepository)
        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

        if (intent.getStringExtra("email") != null && intent.getStringExtra("password") != null) {
            email = intent.getStringExtra("email")!!
            password = intent.getStringExtra("password")!!
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeComponent())
                .addToBackStack(null)
                .commit()
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainer.id, HomeComponent())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.navigation_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainer.id, SearchComponent())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.navigation_notifications -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainer.id, NotificationComponent())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.navigation_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainer.id, ProfileComponent())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainer.id, HomeComponent())
                        .addToBackStack(null)
                        .commit()
                    true
                }
            }
        }

        mainViewModel.fetchPopularMovies()
        mainViewModel.fetchTopRateMovie()
        mainViewModel.fetchUpcomingMovie()
        mainViewModel.fetchNowPlayingMovie()
        loginViewModel.getUserData(email)
        loginViewModel.saveUserEmail(email)

        mainViewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        loginViewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
