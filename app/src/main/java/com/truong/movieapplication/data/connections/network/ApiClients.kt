package com.truong.movieapplication.data.connections.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClients {
    val dataInstance: MovieApiServices by lazy {
        val data = Retrofit.Builder()
            .baseUrl(Base.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        data.create(MovieApiServices::class.java)
    }
}