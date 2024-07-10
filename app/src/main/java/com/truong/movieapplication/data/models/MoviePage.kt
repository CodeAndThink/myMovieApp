package com.truong.movieapplication.data.models

data class MoviePage(
    val page: Long,
    val results: List<Movie>,
    val total_pages: Long,
    val total_results: Long
)
