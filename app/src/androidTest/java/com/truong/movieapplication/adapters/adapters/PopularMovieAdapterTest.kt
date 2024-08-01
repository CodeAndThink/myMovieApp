package com.truong.movieapplication.adapters.adapters

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.viewpager2.widget.ViewPager2
import com.truong.movieapplication.R
import com.truong.movieapplication.data.models.Message
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.models.MovieGenre
import com.truong.movieapplication.data.models.MoviePage
import com.truong.movieapplication.ui.adapters.MessageAdapter
import com.truong.movieapplication.ui.adapters.PopularMovieAdapter
import com.truong.movieapplication.ui.mainscreen.MainActivity
import com.truong.movieapplication.ui.mainscreen.notification.MessageDetailActivity
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class PopularMovieAdapterTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var adapter: PopularMovieAdapter

    private val mockListMovie = listOf(
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
    )

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val recyclerView: ViewPager2 = activity.findViewById(R.id.popular_movie_recycle_view)
                adapter = PopularMovieAdapter(mockListMovie, recyclerView)
                recyclerView.adapter = adapter
            }
        }
    }

    @Test
    fun testItemCount() {
        activityScenarioRule.scenario.onActivity { activity ->
            val recyclerView: RecyclerView = activity.findViewById(R.id.popular_movie_recycle_view)
            assertEquals(1, recyclerView.adapter?.itemCount)
        }
    }

    @Test
    fun testViewHolderBinding() {
        activityScenarioRule.scenario.onActivity { activity ->
            val recyclerView: RecyclerView = activity.findViewById(R.id.popular_movie_recycle_view)
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(0) as PopularMovieAdapter.ViewHolder

            val movieImage = viewHolder.itemView.findViewById<ImageView>(R.id.movie_big_pic)
            val movieName = viewHolder.itemView.findViewById<TextView>(R.id.movie_big_name)
            val movieDate = viewHolder.itemView.findViewById<TextView>(R.id.movie_big_date)
            val movieRatingBar = viewHolder.itemView.findViewById<RatingBar>(R.id.movie_big_rating)
            val movieRatingScore = viewHolder.itemView.findViewById<TextView>(R.id.movie_big_rating_score)

            assertEquals("Movie 2", movieName.text.toString())
            assertEquals("this/is/the/poster/path", movieImage.toString())
            assertEquals("this is the overview", movieName.text.toString())
            assertEquals("2022-01-01", movieDate.text.toString())
            assertEquals(0.0f, movieRatingBar.rating)
            assertEquals("0/10", movieRatingScore.text.toString())
        }
    }

    /*@Test
    fun testItemViewClick() {
        Intents.init()

        onView(withId(R.id.popular_movie_recycle_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<PopularMovieAdapter.ViewHolder>(0, click()))

        Intents.intended(
            allOf(
                hasComponent(PopularMovieAdapter::class.java.name),
            )
        )

        Intents.release()
    }*/
}