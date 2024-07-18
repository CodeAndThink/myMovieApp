package com.truong.movieapplication.connections.networks

import com.truong.movieapplication.data.connections.network.MovieApiServices
import com.truong.movieapplication.data.models.ListMovieGenre
import com.truong.movieapplication.data.models.Movie
import com.truong.movieapplication.data.models.MovieGenre
import com.truong.movieapplication.data.models.MoviePage
import com.truong.movieapplication.data.models.TrailerMovie
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class MovieApiServicesTest {

    @Mock
    private lateinit var mockApiService: MovieApiServices

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: MovieApiServices

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(MovieApiServices::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetPopularMovies() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")

        mockWebServer.enqueue(mockResponse)

        val call: Call<MoviePage> = apiService.getPopularMovies()
        val response = call.execute()

        assertEquals(200, response.code())
        assertEquals(1L, response.body()?.page)
        assertEquals(0L, response.body()?.total_results)
    }

    @Test
    fun testGetTopRatedMovies() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")

        mockWebServer.enqueue(mockResponse)

        val call: Call<MoviePage> = apiService.getTopRatedMovies()
        val response = call.execute()

        assertEquals(200, response.code())
        assertEquals(1L, response.body()?.page)
        assertEquals(0L, response.body()?.total_results)
    }

    @Test
    fun testGetUpcomingMovies() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")

        mockWebServer.enqueue(mockResponse)

        val call: Call<MoviePage> = apiService.getUpcomingMovies()
        val response = call.execute()

        assertEquals(200, response.code())
        assertEquals(1L, response.body()?.page)
        assertEquals(0L, response.body()?.total_results)
    }

    @Test
    fun testGetNowPlayingMovies() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")

        mockWebServer.enqueue(mockResponse)

        val call: Call<MoviePage> = apiService.getNowPlayingMovies()
        val response = call.execute()

        assertEquals(200, response.code())
        assertEquals(1L, response.body()?.page)
        assertEquals(0L, response.body()?.total_results)
    }

    @Test
    fun testGetGenreMovies() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{\"genres\":[{\"id\":1,\"name\":\"Action\"}]}")

        mockWebServer.enqueue(mockResponse)

        val call: Call<ListMovieGenre> = apiService.getGenreMovies()
        val response = call.execute()

        assertEquals(200, response.code())
        response.body()?.genres?.get(0)?.let { assertEquals(1L, it.id) }
        response.body()?.genres?.get(0)?.let { assertEquals("Action", it.name) }
    }

    @Test
    fun testGetMovieDetails() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
        {
            "id": 123,
            "title": "Sample Movie",
            "genre_ids": [1, 2, 3],
            "genres": [
                {"id": 1, "name": "Action"},
                {"id": 2, "name": "Adventure"},
                {"id": 3, "name": "Sci-Fi"}
            ],
            "poster_path": "/path/to/poster.jpg",
            "overview": "This is a sample movie overview.",
            "release_date": "2023-01-01",
            "vote_average": 7.5,
            "vote_count": 1000,
            "popularity": 8.2,
            "adult": false,
            "backdrop_path": "/path/to/backdrop.jpg",
            "original_language": "en",
            "original_title": "Sample Movie",
            "video": false
        }
    """.trimIndent())

        mockWebServer.enqueue(mockResponse)
        val movieId = 123L

        val call: Call<Movie> = apiService.getMovieDetails(movieId = movieId)
        val response = call.execute()

        assertEquals(200, response.code())
        assertEquals(123L, response.body()?.id)
        assertEquals(false, response.body()?.adult)
        assertEquals("2023-01-01", response.body()?.release_date)
    }

    @Test
    fun testSearchMovie() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")

        mockWebServer.enqueue(mockResponse)
        val queryString = "tr"

        val call: Call<MoviePage> = apiService.searchMovie(query = queryString)
        val response = call.execute()

        assertEquals(200, response.code())
        assertEquals(1L, response.body()?.page)
        assertEquals(0L, response.body()?.total_results)
    }

    @Test
    fun testGetMovieTrailer() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{\"id\":1,\"results\":[]}")

        mockWebServer.enqueue(mockResponse)

        val call: Call<TrailerMovie> = apiService.getMovieTrailer(movieId = 123L)
        val response = call.execute()

        assertEquals(200, response.code())
        assertEquals(1L, response.body()?.id)
        assertEquals(emptyList<MovieGenre>(), response.body()?.results)
    }

    @Test
    fun testGetMovieImage() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("123")

        mockWebServer.enqueue(mockResponse)

        val id = "123"

        val call: Call<ResponseBody> = apiService.getMovieImage(idImage = id)
        val response = call.execute()

        assertEquals(200, response.code())
        assertNotNull(response.body())
        assertEquals("123", response.body()?.string())
    }
}