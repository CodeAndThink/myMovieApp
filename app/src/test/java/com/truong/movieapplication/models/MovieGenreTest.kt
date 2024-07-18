package com.truong.movieapplication.models

import android.os.Parcel
import com.truong.movieapplication.data.models.MovieGenre
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MovieGenreTest {

    @Test
    fun testConstructorAndGetters() {
        val genre = MovieGenre(
            id = 1,
            name = "Action"
        )

        assertEquals(1L, genre.id)
        assertEquals("Action", genre.name)
    }

    /*@Test
    fun testParcelable() {
        val originalGenre = MovieGenre(
            id = 2,
            name = "Comedy"
        )

        val parcel = Parcel.obtain()
        originalGenre.writeToParcel(parcel, originalGenre.describeContents())
        parcel.setDataPosition(0)
        val createdGenre = MovieGenre.CREATOR.createFromParcel(parcel)
        assertEquals(originalGenre, createdGenre)
    }*/
}