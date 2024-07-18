package com.truong.movieapplication

import com.truong.movieapplication.data.models.ListMovieGenre
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.models.MovieGenre
import com.truong.movieapplication.data.models.MoviePage
import com.truong.movieapplication.data.models.TrailerDetail
import com.truong.movieapplication.data.models.TrailerMovie
import com.truong.movieapplication.data.models.User

object MockData {
    val mockMovieId = 123L

    val mockMovie = Movie(
        1L,
        "Movie 1",
        listOf(1, 2, 3),
        null,
        null,
        null,
        null,
        0.0f,
        0,
        0.0f,
        false,
        null,
        null,
        null,
        false
    )

    val mockMoviePage = MoviePage(
        1, results = listOf(
            Movie(
                1L,
                "Movie 1",
                listOf(1, 2, 3),
                null,
                null,
                null,
                null,
                0.0f,
                0,
                0.0f,
                false,
                null,
                null,
                null,
                false
            ),
            Movie(
                2L,
                "Movie 2",
                listOf(4, 5, 6),
                listOf(MovieGenre(1L, "Action"), MovieGenre(2L, "Comedy")),
                "this/is/the/poster/path",
                "this is the overview",
                "2022-01-01",
                0.0f,
                0,
                0.0f,
                false,
                null,
                null,
                null,
                false
            )
        ), 10, 20
    )

    val mockMessage = "Error message"

    val mockGenre = ListMovieGenre(
        listOf(MovieGenre(1L, "Action"), MovieGenre(2L, "Comedy"))
    )

    val mockTrailer = TrailerMovie(
        123L, listOf(
            TrailerDetail(
                id = "abc123",
                key = "xyz456",
                name = "Trailer 1",
                site = "YouTube",
                size = 1080,
                type = "Trailer",
                official = true,
                published_at = "2023-01-01"
            )
        )
    )

    val mockUser = User(
        id = 1,
        display_name = "John Doe",
        email = "john.doe@example.com",
        password = "password123",
        avatar = "avatar_url",
        wish_list = listOf(1L, 2L),
        dob = "1990-01-01",
        gender = true,
        address = "123 Street, City",
        phone = "+1234567890"
    )
}