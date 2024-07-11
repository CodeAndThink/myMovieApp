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
    fun getPopularMovies(
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE,
        @Query("page") page: Long = Base.PAGE
    ): Call<MoviePage>

    @GET("3/movie/top_rated")
    fun getTopRatedMovies(
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE,
        @Query("page") page: Long = Base.PAGE
    ): Call<MoviePage>

    @GET("3/movie/upcoming")
    fun getUpcomingMovies(
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE,
        @Query("page") page: Long = Base.PAGE
    ): Call<MoviePage>

    @GET("3/movie/now_playing")
    fun getNowPlayingMovies(
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE,
        @Query("page") page: Long = Base.PAGE
    ): Call<MoviePage>

    @GET("3/genre/movie/list")
    fun getGenreMovies(
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE
    ): Call<ListMovieGenre>

    @GET("3/movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE,
    ): Call<Movie>

    @GET("3/search/movie")
    fun searchMovie(
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("query") query: String,
        @Query("include_adult") adult: String = Base.INCLUDE_ADULT,
        @Query("language") language: String = Base.LANGUAGE,
        @Query("page") page: Long = Base.PAGE
    ): Call<MoviePage>

    @GET("3/movie/{movie_id}/videos")
    fun getMovieTrailer(
        @Path("movie_id") movieId: Long,
        @Header("Authorization") authHeader: String = Base.AUTH_HEADER,
        @Query("language") language: String = Base.LANGUAGE,
    ): Call<TrailerMovie>

    @GET("{size}/{id_image}")
    fun getMovieImage(
        @Path("size") size: String = "w500",
        @Path("id_image") idImage: String
    ): Call<ResponseBody>
}