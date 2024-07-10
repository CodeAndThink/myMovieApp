package com.truong.movieapplication.ui.mainscreen.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.models.MoviePage
import com.truong.movieapplication.data.models.TrailerDetail
import com.truong.movieapplication.data.models.TrailerMovie
import com.truong.movieapplication.data.respository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> get() = _popularMovies

    private val _searchResult = MutableLiveData<List<Movie>>()
    val searchResult: LiveData<List<Movie>> get() = _searchResult

    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>> get() = _topRatedMovies

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>> get() = _upcomingMovies

    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: LiveData<List<Movie>> get() = _nowPlayingMovies

    private val _movieDetails = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie> get() = _movieDetails

    private val _wishList = MutableLiveData<List<Movie>>()
    val wishList: LiveData<List<Movie>> get() = _wishList

    private val _movieTrailer = MutableLiveData<List<TrailerDetail>>()
    val movieTrailer: LiveData<List<TrailerDetail>> get() = _movieTrailer

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private var TAG = "MainViewModel"

    fun getMoviesDetails(movieIds: List<Long>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movieCalls = movieIds.map { movieId ->
                    async {
                        repository.getMovieDetails(movieId).execute().body()
                    }
                }
                val movieList = movieCalls.awaitAll().filterNotNull()
                _wishList.postValue(movieList)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to fetch movie details: ${e.message}")
            }
        }
    }

    fun fetchPopularMovies() {
        repository.getPopularMovies().enqueue(object : Callback<MoviePage> {
            override fun onResponse(call: Call<MoviePage>, response: Response<MoviePage>) {
                if (response.isSuccessful) {
                    _popularMovies.value = response.body()?.results
                } else {
                    _errorMessage.value = "Failed to fetch popular movies"
                }
            }

            override fun onFailure(call: Call<MoviePage>, t: Throwable) {
                _errorMessage.value = t.message
            }
        })
    }

    fun fetchTopRateMovie() {
        repository.getTopRatedMovies().enqueue(object : Callback<MoviePage> {
            override fun onResponse(call: Call<MoviePage>, response: Response<MoviePage>) {
                if (response.isSuccessful) {
                    _topRatedMovies.value = response.body()?.results
                } else {
                    _errorMessage.value = "Failed to fetch popular movies"
                }
            }

            override fun onFailure(call: Call<MoviePage>, t: Throwable) {
                _errorMessage.value = t.message
            }
        })
    }

    fun fetchUpcomingMovie() {
        repository.getUpcomingMovies().enqueue(object : Callback<MoviePage> {
            override fun onResponse(call: Call<MoviePage>, response: Response<MoviePage>) {
                if (response.isSuccessful) {
                    _upcomingMovies.value = response.body()?.results
                } else {
                    _errorMessage.value = "Failed to fetch popular movies"
                }
            }

            override fun onFailure(call: Call<MoviePage>, t: Throwable) {
                _errorMessage.value = t.message
            }
        })
    }

    fun fetchNowPlayingMovie() {
        repository.getNowPlayingMovies().enqueue(object : Callback<MoviePage> {
            override fun onResponse(call: Call<MoviePage>, response: Response<MoviePage>) {
                if (response.isSuccessful) {
                    _nowPlayingMovies.value = response.body()?.results
                } else {
                    _errorMessage.value = "Failed to fetch popular movies"
                }
            }

            override fun onFailure(call: Call<MoviePage>, t: Throwable) {
                _errorMessage.value = t.message
            }
        })
    }

    fun fetchTrailerMovie(id: Long) {
        repository.getMovieTrailer(id).enqueue(object : Callback<TrailerMovie> {
            override fun onResponse(call: Call<TrailerMovie>, response: Response<TrailerMovie>) {
                if (response.isSuccessful) {
                    _movieTrailer.value = response.body()?.results
                } else {
                    _errorMessage.value = "Failed to fetch movie's trailer"
                }
            }

            override fun onFailure(call: Call<TrailerMovie>, t: Throwable) {
                _errorMessage.value = t.message
            }
        })
    }

    fun searchMovies(input: String) {
        repository.searchMovie(input).enqueue(object : Callback<MoviePage> {
            override fun onResponse(call: Call<MoviePage>, response: Response<MoviePage>) {
                if (response.isSuccessful) {
                    _searchResult.value = response.body()?.results
                } else {
                    _errorMessage.value = "Failed to search movies"
                }
            }

            override fun onFailure(call: Call<MoviePage>, t: Throwable) {
                _errorMessage.value = t.message
            }
        })
    }
}