package com.truong.movieapplication.models

import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.models.MovieGenre
import com.truong.movieapplication.data.models.MoviePage
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MoviePageTest {

    @Test
    fun testConstructorAndGetters() {
        val movie1 = Movie(1L, "Movie 1", listOf(1, 2, 3), null, null, null, null, 0.0f, 0, 0.0f, false, null, null, null, false)
        val movie2 = Movie(2L, "Movie 2", listOf(4, 5, 6), listOf(MovieGenre(1L, "Action"), MovieGenre(2L, "Comedy")), "this/is/the/poster/path", "this is the overview", "2022-01-01", 0.0f, 0, 0.0f, false, null, null, null, false)

        val moviePage = MoviePage(
            page = 1,
            results = listOf(movie1, movie2),
            total_pages = 10,
            total_results = 20
        )

        assertEquals(1, moviePage.page)
        assertEquals(2, moviePage.results.size)
        assertEquals(movie1, moviePage.results[0])
        assertEquals(movie2, moviePage.results[1])
        assertEquals(10, moviePage.total_pages)
        assertEquals(20, moviePage.total_results)
    }
}