package com.tube_hunter.frontend.data.repository

import com.tube_hunter.frontend.data.model.Welcome
import com.tube_hunter.frontend.ui.component.SpotAdded
import com.tube_hunter.frontend.ui.component.SpotDetailsUi
import com.tube_hunter.frontend.ui.screen.newspot.SpotFormState
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TubeHunterApi {
    @GET("api/spots")
    suspend fun getAllSpots(): List<Welcome>

    @POST("api/spots")
    suspend fun addSpot(@Body spot: SpotDetailsUi)
}