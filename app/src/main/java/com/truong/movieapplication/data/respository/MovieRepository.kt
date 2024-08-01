package com.truong.movieapplication.data.respository

import com.truong.movieapplication.data.connections.network.MovieApiServices
import com.truong.movieapplication.data.models.ListMovieGenre
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.models.MoviePage
import com.truong.movieapplication.data.models.TrailerMovie
import retrofit2.Call

class MovieRepository(private val movieApiServices: MovieApiServices) {
    suspend fun getPopularMovies(): MoviePage {
        return movieApiServices.getPopularMovies()
    }

    suspend fun getTopRatedMovies(): MoviePage {
        return movieApiServices.getTopRatedMovies()
    }

    suspend fun getUpcomingMovies(): MoviePage {
        return movieApiServices.getUpcomingMovies()
    }

    suspend fun getNowPlayingMovies(): MoviePage {
        return movieApiServices.getNowPlayingMovies()
    }

    suspend fun searchMovie(query: String): MoviePage {
        return movieApiServices.searchMovie(query = query)
    }

    suspend fun getMovieTrailer(movieId: Long): TrailerMovie {
        return movieApiServices.getMovieTrailer(movieId)
    }

    suspend fun getMovieDetails(movieId: Long): Movie {
        return movieApiServices.getMovieDetails(movieId)
    }

    suspend fun getGenreMovies() : ListMovieGenre {
        return movieApiServices.getGenreMovies()
    }
}