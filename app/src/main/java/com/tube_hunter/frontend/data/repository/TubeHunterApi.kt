package com.tube_hunter.frontend.data.repository

import com.tube_hunter.frontend.data.model.Welcome
import retrofit2.http.GET

interface TubeHunterApi {
    @GET("api/spots")
    suspend fun getAllSpots(): List<Welcome>
}