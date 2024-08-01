package com.truong.movieapplication.data.connections.network

import com.truong.movieapplication.data.models.ListMovieGenre
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.models.MoviePage
import com.truong.movieapplication.data.models.TrailerMovie
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiServices {
    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE,
        @Query("page") page: Long = Base.PAGE
    ): MoviePage

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE,
        @Query("page") page: Long = Base.PAGE
    ): MoviePage

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE,
        @Query("page") page: Long = Base.PAGE
    ): MoviePage

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE,
        @Query("page") page: Long = Base.PAGE
    ): MoviePage

    @GET("3/genre/movie/list")
     suspend fun getGenreMovies(
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE
    ): ListMovieGenre

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE,
    ): Movie

    @GET("3/search/movie")
    suspend fun searchMovie(
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("query") query: String,
        @Query("include_adult") adult: String = Base.INCLUDE_ADULT,
        @Query("language") language: String = Base.LANGUAGE,
        @Query("page") page: Long = Base.PAGE
    ): MoviePage

    @GET("3/movie/{movie_id}/videos")
    suspend fun getMovieTrailer(
        @Path("movie_id") movieId: Long,
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE,
    ): TrailerMovie

    @GET("{size}/{id_image}")
    suspend fun getMovieImage(
        @Path("size") size: String = "w500",
        @Path("id_image") idImage: String
    ): ResponseBody
}