package com.truong.movieapplication.data.respository

import com.truong.movieapplication.data.connections.network.MovieApiServices
import com.truong.movieapplication.data.models.ListMovieGenre
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.models.MoviePage
import com.truong.movieapplication.data.models.TrailerMovie
import retrofit2.Call

class MovieRepository(private val movieApiServices: MovieApiServices) {
    fun getPopularMovies(): Call<MoviePage> {
        return movieApiServices.getPopularMovies()
    }

    fun getTopRatedMovies(): Call<MoviePage> {
        return movieApiServices.getTopRatedMovies()
    }

    fun getUpcomingMovies(): Call<MoviePage> {
        return movieApiServices.getUpcomingMovies()
    }

    fun getNowPlayingMovies(): Call<MoviePage> {
        return movieApiServices.getNowPlayingMovies()
    }

    fun searchMovie(query: String): Call<MoviePage> {
        return movieApiServices.searchMovie(query = query)
    }

    fun getMovieTrailer(movieId: Long): Call<TrailerMovie> {
        return movieApiServices.getMovieTrailer(movieId)
    }

    fun getMovieDetails(movieId: Long): Call<Movie> {
        return movieApiServices.getMovieDetails(movieId)
    }

    fun getGenreMovies() : Call<ListMovieGenre> {
        return movieApiServices.getGenreMovies()
    }
}