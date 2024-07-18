package com.truong.movieapplication.models

import com.truong.movieapplication.data.models.Converters
import com.truong.movieapplication.data.models.MovieGenre
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class ConvertersTest {

    private lateinit var converters: Converters

    @Before
    fun setUp() {
        converters = Converters()
    }

    @Test
    fun testFromStringToList() {
        val jsonString = "[1, 2, 3]"
        val expectedList = listOf(1L, 2L, 3L)
        val result = converters.fromString(jsonString)
        assertEquals(expectedList, result)
    }

    @Test
    fun testFromListToString() {
        val list = listOf(1L, 2L, 3L)
        val expectedJsonString = "[1,2,3]"
        val result = converters.fromList(list)
        assertEquals(expectedJsonString, result)
    }

    @Test
    fun testFromMovieGenreListToString() {
        val genres = listOf(MovieGenre(1, "Action"), MovieGenre(2, "Comedy"))
        val expectedJsonString = "[{\"id\":1,\"name\":\"Action\"},{\"id\":2,\"name\":\"Comedy\"}]"
        val result = converters.fromMovieGenreList(genres)
        assertEquals(expectedJsonString, result)
    }

    @Test
    fun testToMovieGenreListFromString() {
        val jsonString = "[{\"id\":1,\"name\":\"Action\"},{\"id\":2,\"name\":\"Comedy\"}]"
        val expectedGenres = listOf(MovieGenre(1, "Action"), MovieGenre(2, "Comedy"))
        val result = converters.toMovieGenreList(jsonString)
        assertEquals(expectedGenres, result)
    }

    @Test
    fun testFromStringWithNull() {
        assertNull(converters.fromString(null))
    }

    @Test
    fun testFromListWithNull() {
        assertNull(converters.fromList(null))
    }

    @Test
    fun testFromMovieGenreListWithNull() {
        assertNull(converters.fromMovieGenreList(null))
    }

    @Test
    fun testToMovieGenreListWithNull() {
        assertNull(converters.toMovieGenreList(null))
    }
}