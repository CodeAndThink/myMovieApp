package com.truong.movieapplication.models

import android.os.Parcel
import com.truong.movieapplication.data.models.ListMovieGenre
import com.truong.movieapplication.data.models.MovieGenre
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock

class ListMovieGenreTest {

    @Test
    fun testConstructorAndGetters() {
        val genre1 = MovieGenre(id = 1, name = "Action")
        val genre2 = MovieGenre(id = 2, name = "Comedy")

        val listMovieGenre = ListMovieGenre(
            genres = listOf(genre1, genre2)
        )

        assertEquals(2, listMovieGenre.genres.size)
        assertEquals(genre1, listMovieGenre.genres[0])
        assertEquals(genre2, listMovieGenre.genres[1])
    }

    @Test
    fun testEquality() {
        val genre1 = MovieGenre(id = 1, name = "Action")
        val genre2 = MovieGenre(id = 2, name = "Comedy")

        val listMovieGenre1 = ListMovieGenre(
            genres = listOf(genre1, genre2)
        )

        val listMovieGenre2 = ListMovieGenre(
            genres = listOf(genre1, genre2)
        )

        assertEquals(listMovieGenre1, listMovieGenre2)
    }

    @Test
    fun testToString() {
        val genre1 = MovieGenre(id = 1, name = "Action")
        val genre2 = MovieGenre(id = 2, name = "Comedy")

        val listMovieGenre = ListMovieGenre(
            genres = listOf(genre1, genre2)
        )

        assertEquals(
            "ListMovieGenre(genres=[MovieGenre(id=1, name=Action), MovieGenre(id=2, name=Comedy)])",
            listMovieGenre.toString()
        )
    }
}
