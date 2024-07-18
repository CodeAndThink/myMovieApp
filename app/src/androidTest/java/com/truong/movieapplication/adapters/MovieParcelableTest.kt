package com.truong.movieapplication.adapters

import android.os.Parcel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.models.MovieGenre
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieParcelableTest {

    @Test
    fun testMovieParcelable() {
        val movie = Movie(2L, "Movie 2", listOf(4, 5, 6), listOf(MovieGenre(1L, "Action"), MovieGenre(2L, "Comedy")), "this/is/the/poster/path", "this is the overview", "2022-01-01", 0.0f, 0, 0.0f, false, null, null, null, false)

        val parcel = Parcel.obtain()
        movie.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)

        val createdFromParcel: Movie = Movie.CREATOR.createFromParcel(parcel)
        parcel.recycle()

        assertEquals(movie, createdFromParcel)
    }
}