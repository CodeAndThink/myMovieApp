package com.truong.movieapplication.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.truong.movieapplication.MockData
import com.truong.movieapplication.MockData.mockGenre
import com.truong.movieapplication.MockData.mockMessage
import com.truong.movieapplication.MockData.mockMoviePage
import com.truong.movieapplication.MockData.mockTrailer
import com.truong.movieapplication.data.models.ListMovieGenre
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.models.MovieGenre
import com.truong.movieapplication.data.models.MoviePage
import com.truong.movieapplication.data.models.TrailerDetail
import com.truong.movieapplication.data.models.TrailerMovie
import com.truong.movieapplication.data.respository.MovieRepository
import com.truong.movieapplication.ui.mainscreen.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var movieObserver: Observer<List<Movie>>
    private lateinit var messageObserver: Observer<String>
    private lateinit var genreObserver: Observer<List<MovieGenre>>
    private lateinit var trailerObserver: Observer<List<TrailerDetail>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        movieObserver = Observer {}
        messageObserver = Observer {}
        genreObserver = Observer {}
        trailerObserver = Observer {}

        viewModel = MainViewModel(repository)
        viewModel.popularMovies.observeForever(movieObserver)
        viewModel.searchResult.observeForever(movieObserver)
        viewModel.topRatedMovies.observeForever(movieObserver)
        viewModel.upcomingMovies.observeForever(movieObserver)
        viewModel.nowPlayingMovies.observeForever(movieObserver)
        viewModel.wishList.observeForever(movieObserver)
        viewModel.movieTrailer.observeForever(trailerObserver)
        viewModel.genreMovies.observeForever(genreObserver)
        viewModel.errorMessage.observeForever(messageObserver)

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun teardown() {
        viewModel.popularMovies.removeObserver(movieObserver)
        viewModel.searchResult.removeObserver(movieObserver)
        viewModel.topRatedMovies.removeObserver(movieObserver)
        viewModel.upcomingMovies.removeObserver(movieObserver)
        viewModel.nowPlayingMovies.removeObserver(movieObserver)
        viewModel.wishList.removeObserver(movieObserver)
        viewModel.movieTrailer.removeObserver(trailerObserver)
        viewModel.genreMovies.removeObserver(genreObserver)
        viewModel.errorMessage.removeObserver(messageObserver)

        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `test fetch popular movies`() {
        testScope.runBlockingTest {
            val mockResponse = Response.success(mockMoviePage)
            `when`(repository.getPopularMovies()).thenReturn(object : Call<MoviePage> {
                override fun enqueue(callback: Callback<MoviePage>) {
                    testScope.launch {
                        callback.onResponse(MockCall.mockMoviePageCall, mockResponse)
                    }
                }
                override fun clone(): Call<MoviePage> = this
                override fun execute(): Response<MoviePage> = mockResponse
                override fun isExecuted(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun isCanceled(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun request(): Request {
                    TODO("Not yet implemented")
                }

                override fun timeout(): Timeout {
                    TODO("Not yet implemented")
                }
            })

            viewModel.fetchPopularMovies()

            val movies = viewModel.popularMovies.value
            Assert.assertEquals(mockMoviePage.results, movies)
        }
    }

    @Test
    fun `test fetch top rate movies`() {
        testScope.runBlockingTest {
            val mockResponse = Response.success(mockMoviePage)
            `when`(repository.getTopRatedMovies()).thenReturn(object : Call<MoviePage> {
                override fun enqueue(callback: Callback<MoviePage>) {
                    testScope.launch {
                        callback.onResponse(MockCall.mockMoviePageCall, mockResponse)
                    }
                }
                override fun clone(): Call<MoviePage> = this
                override fun execute(): Response<MoviePage> = mockResponse
                override fun isExecuted(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun isCanceled(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun request(): Request {
                    TODO("Not yet implemented")
                }

                override fun timeout(): Timeout {
                    TODO("Not yet implemented")
                }
            })

            viewModel.fetchTopRateMovie()

            val movies = viewModel.topRatedMovies.value
            Assert.assertEquals(mockMoviePage.results, movies)
        }
    }

    @Test
    fun `test fetch now playing movies`() {
        testScope.runBlockingTest {
            val mockResponse = Response.success(mockMoviePage)
            `when`(repository.getNowPlayingMovies()).thenReturn(object : Call<MoviePage> {
                override fun enqueue(callback: Callback<MoviePage>) {
                    testScope.launch {
                        callback.onResponse(MockCall.mockMoviePageCall, mockResponse)
                    }
                }
                override fun clone(): Call<MoviePage> = this
                override fun execute(): Response<MoviePage> = mockResponse
                override fun isExecuted(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun isCanceled(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun request(): Request {
                    TODO("Not yet implemented")
                }

                override fun timeout(): Timeout {
                    TODO("Not yet implemented")
                }
            })

            viewModel.fetchNowPlayingMovie()

            val movies = viewModel.nowPlayingMovies.value
            Assert.assertEquals(mockMoviePage.results, movies)
        }
    }

    @Test
    fun `test fetch now playing movies with error`() {
        testScope.runBlockingTest {
            val mockResponse = Response.success(mockMoviePage)
            `when`(repository.getNowPlayingMovies()).thenReturn(object : Call<MoviePage> {
                override fun enqueue(callback: Callback<MoviePage>) {
                    testScope.launch {
                        callback.onFailure(MockCall.mockMoviePageCall, Throwable("Error fetching data"))
                    }
                }
                override fun clone(): Call<MoviePage> = this
                override fun execute(): Response<MoviePage> = mockResponse
                override fun isExecuted(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun isCanceled(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun request(): Request {
                    TODO("Not yet implemented")
                }

                override fun timeout(): Timeout {
                    TODO("Not yet implemented")
                }
            })

            viewModel.fetchNowPlayingMovie()

            val errorMessage = viewModel.errorMessage.value
            Assert.assertEquals("Error fetching data", errorMessage)
            Assert.assertNull(viewModel.nowPlayingMovies.value)
        }
    }

    @Test
    fun `test fetch up comming movies`() {
        testScope.runBlockingTest {
            val mockResponse = Response.success(mockMoviePage)
            `when`(repository.getUpcomingMovies()).thenReturn(object : Call<MoviePage> {
                override fun enqueue(callback: Callback<MoviePage>) {
                    testScope.launch {
                        callback.onResponse(MockCall.mockMoviePageCall, mockResponse)
                    }
                }
                override fun clone(): Call<MoviePage> = this
                override fun execute(): Response<MoviePage> = mockResponse
                override fun isExecuted(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun isCanceled(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun request(): Request {
                    TODO("Not yet implemented")
                }

                override fun timeout(): Timeout {
                    TODO("Not yet implemented")
                }
            })

            viewModel.fetchUpcomingMovie()

            val movies = viewModel.upcomingMovies.value
            Assert.assertEquals(mockMoviePage.results, movies)
        }
    }

    @Test
    fun `test fetch up comming movies with error`() {
        val mockErrorResponse = Response.error<ListMovieGenre>(404, ResponseBody.create(null, "Error"))
        `when`(repository.getGenreMovies()).thenReturn(object : Call<ListMovieGenre> {
            override fun enqueue(callback: Callback<ListMovieGenre>) {
                testScope.launch {
                    callback.onResponse(MockCall.mockGenreCall, mockErrorResponse)
                    callback.onFailure(MockCall.mockGenreCall, Throwable("Failed to fetch movie's genre"))
                }
            }
            override fun clone(): Call<ListMovieGenre> = this
            override fun execute(): Response<ListMovieGenre> = mockErrorResponse
            override fun isExecuted(): Boolean {
                TODO("Not yet implemented")
            }

            override fun cancel() {
                TODO("Not yet implemented")
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun request(): Request {
                TODO("Not yet implemented")
            }

            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }
        })

        viewModel.fetchMovieGenre()

        val errorMessage = viewModel.errorMessage.value
        Assert.assertEquals("Failed to fetch movie's genre", errorMessage)
    }

    @Test
    fun `test fetch movie genre movies`() {
        testScope.runBlockingTest {
            val mockResponse = Response.success(mockGenre)
            `when`(repository.getGenreMovies()).thenReturn(object : Call<ListMovieGenre> {
                override fun enqueue(callback: Callback<ListMovieGenre>) {
                    testScope.launch {
                        callback.onResponse(MockCall.mockGenreCall, mockResponse)
                    }
                }
                override fun clone(): Call<ListMovieGenre> = this
                override fun execute(): Response<ListMovieGenre> = mockResponse
                override fun isExecuted(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun isCanceled(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun request(): Request {
                    TODO("Not yet implemented")
                }

                override fun timeout(): Timeout {
                    TODO("Not yet implemented")
                }
            })

            viewModel.fetchMovieGenre()

            val movies = viewModel.genreMovies.value
            Assert.assertEquals(mockGenre.genres, movies)
        }
    }

    @Test
    fun `test fetch movie genre movies with error`() {
        val mockErrorResponse = Response.error<ListMovieGenre>(404, ResponseBody.create(null, "Error"))
        `when`(repository.getGenreMovies()).thenReturn(object : Call<ListMovieGenre> {
            override fun enqueue(callback: Callback<ListMovieGenre>) {
                testScope.launch {
                    callback.onResponse(MockCall.mockGenreCall, mockErrorResponse)
                    callback.onFailure(MockCall.mockGenreCall, Throwable("Failed to fetch movie's genre"))
                }
            }
            override fun clone(): Call<ListMovieGenre> = this
            override fun execute(): Response<ListMovieGenre> = mockErrorResponse
            override fun isExecuted(): Boolean {
                TODO("Not yet implemented")
            }

            override fun cancel() {
                TODO("Not yet implemented")
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun request(): Request {
                TODO("Not yet implemented")
            }

            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }
        })

        viewModel.fetchMovieGenre()

        val errorMessage = viewModel.errorMessage.value
        Assert.assertEquals("Failed to fetch movie's genre", errorMessage)
    }

    @Test
    fun `test fetch movie trailer movies`() {
        testScope.runBlockingTest {
            val mockResponse = Response.success(mockTrailer)
            `when`(repository.getMovieTrailer(MockData.mockMovieId)).thenReturn(object : Call<TrailerMovie> {
                override fun enqueue(callback: Callback<TrailerMovie>) {
                    testScope.launch {
                        callback.onResponse(MockCall.mockTrailerCall, mockResponse)
                    }
                }
                override fun clone(): Call<TrailerMovie> = this
                override fun execute(): Response<TrailerMovie> = mockResponse
                override fun isExecuted(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun isCanceled(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun request(): Request {
                    TODO("Not yet implemented")
                }

                override fun timeout(): Timeout {
                    TODO("Not yet implemented")
                }
            })

            viewModel.fetchTrailerMovie(MockData.mockMovieId)

            val movies = viewModel.movieTrailer.value
            Assert.assertEquals(mockTrailer.results, movies)
        }
    }

    @Test
    fun `test fetch movie trailer movies with error`() {
        testScope.runBlockingTest {
            val mockResponse = Response.error<TrailerMovie>(404, ResponseBody.create(null, "Error"))
            `when`(repository.getMovieTrailer(MockData.mockMovieId)).thenReturn(object : Call<TrailerMovie> {
                override fun enqueue(callback: Callback<TrailerMovie>) {
                    testScope.launch {
                        callback.onResponse(MockCall.mockTrailerCall, mockResponse)
                        callback.onFailure(MockCall.mockTrailerCall, Throwable("Error fetching trailer"))
                    }
                }
                override fun clone(): Call<TrailerMovie> = this
                override fun execute(): Response<TrailerMovie> = mockResponse
                override fun isExecuted(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun isCanceled(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun request(): Request {
                    TODO("Not yet implemented")
                }

                override fun timeout(): Timeout {
                    TODO("Not yet implemented")
                }
            })

            viewModel.fetchTrailerMovie(MockData.mockMovieId)

            val errorMessage = viewModel.errorMessage.value
            Assert.assertEquals("Error fetching trailer", errorMessage)
            Assert.assertNull(viewModel.movieTrailer.value)
        }
    }
}

object MockCall {

    val mockMoviePageCall = object : Call<MoviePage> {
        override fun enqueue(callback: Callback<MoviePage>) {}
        override fun clone(): Call<MoviePage> = this
        override fun execute(): Response<MoviePage> = Response.success(mockMoviePage)
        override fun isExecuted(): Boolean {
            TODO("Not yet implemented")
        }

        override fun cancel() {
            TODO("Not yet implemented")
        }

        override fun isCanceled(): Boolean {
            TODO("Not yet implemented")
        }

        override fun request(): Request {
            TODO("Not yet implemented")
        }

        override fun timeout(): Timeout {
            TODO("Not yet implemented")
        }
    }

    val mockMessageCall = object : Call<String> {
        override fun clone(): Call<String> = this

        override fun execute(): Response<String> = Response.success(mockMessage)

        override fun isExecuted(): Boolean {
            TODO("Not yet implemented")
        }

        override fun cancel() {
            TODO("Not yet implemented")
        }

        override fun isCanceled(): Boolean {
            TODO("Not yet implemented")
        }

        override fun request(): Request {
            TODO("Not yet implemented")
        }

        override fun timeout(): Timeout {
            TODO("Not yet implemented")
        }

        override fun enqueue(callback: Callback<String>) {}
    }

    val mockGenreCall = object : Call<ListMovieGenre> {
        override fun clone(): Call<ListMovieGenre> = this
        override fun execute(): Response<ListMovieGenre> = Response.success(mockGenre)
        override fun enqueue(callback: Callback<ListMovieGenre>) {
            TODO("Not yet implemented")
        }

        override fun isExecuted(): Boolean {
            TODO("Not yet implemented")
        }

        override fun cancel() {
            TODO("Not yet implemented")
        }

        override fun isCanceled(): Boolean {
            TODO("Not yet implemented")
        }

        override fun request(): Request {
            TODO("Not yet implemented")
        }

        override fun timeout(): Timeout {
            TODO("Not yet implemented")
        }
    }

    val mockTrailerCall = object : Call<TrailerMovie> {
        override fun clone(): Call<TrailerMovie> = this

        override fun execute(): Response<TrailerMovie> = Response.success(mockTrailer)

        override fun isExecuted(): Boolean {
            TODO("Not yet implemented")
        }

        override fun cancel() {
            TODO("Not yet implemented")
        }

        override fun isCanceled(): Boolean {
            TODO("Not yet implemented")
        }

        override fun request(): Request {
            TODO("Not yet implemented")
        }

        override fun timeout(): Timeout {
            TODO("Not yet implemented")
        }

        override fun enqueue(callback: Callback<TrailerMovie>) {
            TODO("Not yet implemented")
        }

    }
}