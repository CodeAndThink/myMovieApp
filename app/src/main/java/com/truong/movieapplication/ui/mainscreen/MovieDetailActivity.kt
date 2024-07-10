package com.truong.movieapplication.ui.mainscreen

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.truong.movieapplication.data.connections.local.UserDAO
import com.truong.movieapplication.data.connections.local.UserDatabase
import com.truong.movieapplication.data.connections.network.ApiClients
import com.truong.movieapplication.data.connections.network.Base
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.respository.FirebaseAuthService
import com.truong.movieapplication.data.respository.LoginRepository
import com.truong.movieapplication.data.respository.MovieRepository
import com.truong.movieapplication.data.respository.SharedReferencesHelper
import com.truong.movieapplication.databinding.ActivityMovieDetailUsingRelativeLayoutBinding
import com.truong.movieapplication.ui.login.LoginViewModel
import com.truong.movieapplication.ui.login.LoginViewModelFactory
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModel
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModelFactory

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMovieDetailUsingRelativeLayoutBinding
    private val binding get() = _binding
    private val apiService = ApiClients.dataInstance
    private val repository = MovieRepository(apiService)
    private lateinit var movie: Movie
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginViewModelFactory: LoginViewModelFactory
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }
    private val TAG = "MovieDetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMovieDetailUsingRelativeLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferencesHelper = SharedReferencesHelper(this)
        val dao = UserDatabase.getDatabase(this).userDao()
        val loginRepository = LoginRepository(FirebaseAuthService(), dao, preferencesHelper)
        loginViewModelFactory = LoginViewModelFactory(loginRepository)
        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

        movie = intent.getParcelableExtra<Movie>("movie")!!
        binding.movieDetailName.text = movie.title
        binding.movieDetailYear.text = "(${movie.release_date!!.substring(0, 4)})"
        /*binding.movieDetailCategories.text = movie.!!.joinToString(", ")*/
        binding.movieDetailRating.rating = movie.vote_average / 2
        binding.movieDetailContext.text = movie.overview
        Glide.with(this).load(Base.IMAGE_URL + Base.SIZE + movie.poster_path).into(binding.movieDetailMiniPic)
        Glide.with(this).load(Base.IMAGE_URL + Base.SIZE + movie.poster_path).into(binding.movieDetailBackground)
        binding.movieDetailRatingScore.text = movie.vote_average.toString() + "/10"
        mainViewModel.fetchTrailerMovie(movie.id)
        mainViewModel.movieTrailer.observe(this) { trailer ->
            if (trailer.isNotEmpty()) {
                val videoId = trailer[0].key
                val html = "<html>" +
                        "<body style='margin:0;padding:0;'>" +
                        "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$videoId\" frameborder=\"0\" allowfullscreen></iframe>" +
                        "</body>" +
                        "</html>"
                binding.movieDetailTrailerWebView.settings.javaScriptEnabled = true
                binding.movieDetailTrailerWebView.loadData(html, "text/html", "utf-8")
            }
        }

        binding.movieDetailAddWishListButton.setOnClickListener {
            loginViewModel.getUserEmail()
                ?.let { it1 -> loginViewModel.addToWishList(it1, movie.id) }
        }

        loginViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        mainViewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()

        Glide.with(this).clear(binding.movieDetailMiniPic)
        Glide.with(this).clear(binding.movieDetailBackground)

        binding.movieDetailTrailerWebView.loadData("", "text/html", "utf-8")
    }

    override fun onStop() {
        super.onStop()

        mainViewModel.errorMessage.removeObservers(this)
        mainViewModel.movieTrailer.removeObservers(this)

        loginViewModel.errorMessage.removeObservers(this)
    }
}
