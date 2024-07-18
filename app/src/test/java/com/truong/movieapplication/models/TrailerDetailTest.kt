package com.truong.movieapplication.models

import com.truong.movieapplication.data.models.TrailerDetail
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class TrailerDetailTest {

    @Test
    fun testConstructorAndGetters() {
        val trailer = TrailerDetail(
            id = "abc123",
            key = "xyz456",
            name = "Trailer 1",
            site = "YouTube",
            size = 1080,
            type = "Trailer",
            official = true,
            published_at = "2023-01-01"
        )

        assertEquals("abc123", trailer.id)
        assertEquals("xyz456", trailer.key)
        assertEquals("Trailer 1", trailer.name)
        assertEquals("YouTube", trailer.site)
        assertEquals(1080, trailer.size)
        assertEquals("Trailer", trailer.type)
        assertTrue(trailer.official)
        assertEquals("2023-01-01", trailer.published_at)
    }

    @Test
    fun testEquality() {
        val trailer1 = TrailerDetail(
            id = "abc123",
            key = "xyz456",
            name = "Trailer 1",
            site = "YouTube",
            size = 1080,
            type = "Trailer",
            official = true,
            published_at = "2023-01-01"
        )

        val trailer2 = TrailerDetail(
            id = "abc123",
            key = "xyz456",
            name = "Trailer 1",
            site = "YouTube",
            size = 1080,
            type = "Trailer",
            official = true,
            published_at = "2023-01-01"
        )

        assertEquals(trailer1, trailer2)
    }

    @Test
    fun testToString() {
        val trailer = TrailerDetail(
            id = "abc123",
            key = "xyz456",
            name = "Trailer 1",
            site = "YouTube",
            size = 1080,
            type = "Trailer",
            official = true,
            published_at = "2023-01-01"
        )

        assertEquals(
            "TrailerDetail(id=abc123, key=xyz456, name=Trailer 1, site=YouTube, size=1080, type=Trailer, official=true, published_at=2023-01-01)",
            trailer.toString()
        )
    }
}