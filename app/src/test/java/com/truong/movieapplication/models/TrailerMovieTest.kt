package com.truong.movieapplication.models

import com.truong.movieapplication.data.models.TrailerDetail
import com.truong.movieapplication.data.models.TrailerMovie
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class TrailerMovieTest {

    @Test
    fun testConstructorAndGetters() {
        val trailerDetail1 = TrailerDetail(
            id = "abc123",
            key = "xyz456",
            name = "Trailer 1",
            site = "YouTube",
            size = 1080,
            type = "Trailer",
            official = true,
            published_at = "2023-01-01"
        )

        val trailerDetail2 = TrailerDetail(
            id = "def456",
            key = "uvw789",
            name = "Trailer 2",
            site = "Vimeo",
            size = 720,
            type = "Teaser",
            official = false,
            published_at = "2023-02-01"
        )

        val trailerMovie = TrailerMovie(
            id = 123,
            results = listOf(trailerDetail1, trailerDetail2)
        )

        assertEquals(123, trailerMovie.id)
        assertEquals(2, trailerMovie.results.size)
        assertEquals(trailerDetail1, trailerMovie.results[0])
        assertEquals(trailerDetail2, trailerMovie.results[1])
    }

    @Test
    fun testEquality() {
        val trailerDetail1 = TrailerDetail(
            id = "abc123",
            key = "xyz456",
            name = "Trailer 1",
            site = "YouTube",
            size = 1080,
            type = "Trailer",
            official = true,
            published_at = "2023-01-01"
        )

        val trailerDetail2 = TrailerDetail(
            id = "def456",
            key = "uvw789",
            name = "Trailer 2",
            site = "Vimeo",
            size = 720,
            type = "Teaser",
            official = false,
            published_at = "2023-02-01"
        )

        val trailerMovie1 = TrailerMovie(
            id = 123,
            results = listOf(trailerDetail1, trailerDetail2)
        )

        val trailerMovie2 = TrailerMovie(
            id = 123,
            results = listOf(trailerDetail1, trailerDetail2)
        )

        assertEquals(trailerMovie1, trailerMovie2)
    }

    @Test
    fun testToString() {
        val trailerDetail1 = TrailerDetail(
            id = "abc123",
            key = "xyz456",
            name = "Trailer 1",
            site = "YouTube",
            size = 1080,
            type = "Trailer",
            official = true,
            published_at = "2023-01-01"
        )

        val trailerDetail2 = TrailerDetail(
            id = "def456",
            key = "uvw789",
            name = "Trailer 2",
            site = "Vimeo",
            size = 720,
            type = "Teaser",
            official = false,
            published_at = "2023-02-01"
        )

        val trailerMovie = TrailerMovie(
            id = 123,
            results = listOf(trailerDetail1, trailerDetail2)
        )

        assertEquals(
            "TrailerMovie(id=123, results=[TrailerDetail(id=abc123, key=xyz456, name=Trailer 1, site=YouTube, size=1080, type=Trailer, official=true, published_at=2023-01-01), TrailerDetail(id=def456, key=uvw789, name=Trailer 2, site=Vimeo, size=720, type=Teaser, official=false, published_at=2023-02-01)])",
            trailerMovie.toString()
        )
    }
}