package com.truong.movieapplication.data.respository

import com.truong.movieapplication.data.connections.network.ApiServices
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.models.MoviePage
import com.truong.movieapplication.data.models.TrailerMovie
import retrofit2.Call

class MovieRepository(private val apiServices: ApiServices) {
    fun getPopularMovies(): Call<MoviePage> {
        return apiServices.getPopularMovies()
    }

    fun getTopRatedMovies(): Call<MoviePage> {
        return apiServices.getTopRatedMovies()
    }

    fun getUpcomingMovies(): Call<MoviePage> {
        return apiServices.getUpcomingMovies()
    }

    fun getNowPlayingMovies(): Call<MoviePage> {
        return apiServices.getNowPlayingMovies()
    }

    fun searchMovie(query: String): Call<MoviePage> {
        return apiServices.searchMovie(query = query)
    }

    fun getMovieTrailer(movieId: Long): Call<TrailerMovie> {
        return apiServices.getMovieTrailer(movieId)
    }

    fun getMovieDetails(movieId: Long): Call<Movie> {
        return apiServices.getMovieDetails(movieId)
    }
}