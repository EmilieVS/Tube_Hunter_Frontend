package com.tube_hunter.frontend.data.api

import com.tube_hunter.frontend.data.repository.TubeHunterApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://10.0.2.2:8080/"

    val api: TubeHunterApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TubeHunterApi::class.java)
    }
}