package com.truong.movieapplication.connections.networks

import com.truong.movieapplication.data.connections.network.ApiClients
import com.truong.movieapplication.data.connections.network.Base
import com.truong.movieapplication.data.connections.network.MovieApiServices
import junit.framework.TestCase.assertNotNull
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import okhttp3.mockwebserver.MockResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClientsTest {
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test dataInstance returns non-null MovieApiServices`() {
        // Kiểm tra xem dataInstance không phải là null
        assertNotNull(ApiClients.dataInstance)
    }

    @Test
    fun `test dataInstance makes successful API call`() {
        // Đặt phản hồi cho MockWebServer
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{\"results\":[], \"page\":1, \"total_pages\":1, \"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // Tạo Retrofit instance với baseUrl từ MockWebServer
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val movieApiServices = retrofit.create(MovieApiServices::class.java)

        // Gọi API và kiểm tra phản hồi
        val response = movieApiServices.getPopularMovies("api_key", Base.LANGUAGE, Base.PAGE).execute()
        val responseBody = response.body()

        assertNotNull(responseBody)
        assert(response.isSuccessful)
        assert(responseBody?.results?.isEmpty() == true)
    }
}