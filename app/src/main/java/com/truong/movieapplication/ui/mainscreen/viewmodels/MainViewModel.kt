package com.truong.movieapplication.ui.mainscreen.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.models.MovieGenre
import com.truong.movieapplication.data.models.TrailerDetail
import com.truong.movieapplication.data.respository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private val _genreMovies = MutableLiveData<List<MovieGenre>>()
    val genreMovies: LiveData<List<MovieGenre>> get() = _genreMovies

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private var TAG = "MainViewModel"

    fun setWishList(movieIds: List<Long>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val movieCalls = movieIds.map { movieId ->
                        async {
                            repository.getMovieDetails(movieId)
                        }
                    }
                    val movieList = movieCalls.awaitAll().filterNotNull()
                    _wishList.postValue(movieList)
                } catch (e: Exception) {
                    _errorMessage.postValue("Failed to fetch movie details: ${e.message}")
                }
            }
        }
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val movieResults = withContext(Dispatchers.IO) {
                    repository.getPopularMovies().results
                }

                _popularMovies.postValue(movieResults)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to fetch popular movies: ${e.message}")
            }
        }
    }

    fun fetchTopRateMovie() {
        viewModelScope.launch {
            try {
                val movieResults = withContext(Dispatchers.IO) {
                    repository.getTopRatedMovies().results
                }

                _topRatedMovies.postValue(movieResults)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to fetch top rated movies: ${e.message}")
            }
        }
    }

    fun fetchUpcomingMovie() {
        viewModelScope.launch {
            try {
                val movieResults = withContext(Dispatchers.IO) {
                    repository.getUpcomingMovies().results
                }

                _upcomingMovies.postValue(movieResults)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to fetch upcoming movies: ${e.message}")
            }
        }
    }

    fun fetchNowPlayingMovie() {
        viewModelScope.launch {
            try {
                val movieResults = withContext(Dispatchers.IO) {
                    repository.getNowPlayingMovies().results
                }

                _nowPlayingMovies.postValue(movieResults)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to fetch now playing movies: ${e.message}")
            }
        }
    }

    fun fetchTrailerMovie(id: Long) {
        viewModelScope.launch {
            try {
                val movieResults = withContext(Dispatchers.IO) {
                    repository.getMovieTrailer(id).results
                }

                _movieTrailer.postValue(movieResults)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to fetch popular movies: ${e.message}")
            }
        }
    }

    fun fetchMovieGenre() {
        viewModelScope.launch {
            try {
                val movieResults = withContext(Dispatchers.IO) {
                    repository.getGenreMovies().genres
                }

                _genreMovies.postValue(movieResults)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to fetch movie's genre: ${e.message}")
            }
        }
    }

    fun searchMovies(input: String) {
        viewModelScope.launch {
            try {
                val movieResults = withContext(Dispatchers.IO) {
                    repository.searchMovie(input).results
                }

                _searchResult.postValue(movieResults)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to search movies: ${e.message}")
            }
        }
    }
}