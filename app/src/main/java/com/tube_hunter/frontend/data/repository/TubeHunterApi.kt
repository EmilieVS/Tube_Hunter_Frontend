package com.tube_hunter.frontend.data.repository

import com.tube_hunter.frontend.data.model.Welcome
import com.tube_hunter.frontend.ui.component.SpotAdded
import com.tube_hunter.frontend.ui.component.SpotDetailsUi
import com.tube_hunter.frontend.ui.screen.newspot.SpotFormState
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


data class UploadResponse(
    val photoUrl: String
)
interface TubeHunterApi {
    @GET("api/spots")
    suspend fun getAllSpots(): List<Welcome>

    @POST("api/spots")
    suspend fun addSpot(@Body spot: SpotDetailsUi)

    // Nouvel endpoint pour uploader l'image
    @Multipart
    @POST("api/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<UploadResponse>
}
