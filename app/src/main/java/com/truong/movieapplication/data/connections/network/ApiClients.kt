package com.truong.movieapplication.data.connections.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClients {
    val dataInstance: ApiServices by lazy {
        val data = Retrofit.Builder()
            .baseUrl(Base.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        data.create(ApiServices::class.java)
    }

    val imageInstance: ApiServices by lazy {
        val image = Retrofit.Builder()
            .baseUrl(Base.IMAGE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        image.create(ApiServices::class.java)
    }
}