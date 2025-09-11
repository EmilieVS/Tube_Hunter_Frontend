package com.tube_hunter.frontend.ui.screen.newspot

import ApiError
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.tube_hunter.frontend.data.api.ApiClient
import com.tube_hunter.frontend.ui.component.SpotDetailsUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SpotFormState(
    val imageUri: Uri? = null,
    val spotName: String = "",
    val city: String = "",
    val country: String = "",
    val difficulty: Int = 0,
    val surfBreaks: String = "",
    val seasonStart: Long? = null,
    val seasonEnd: Long? = null
) {
    fun isValid(): Boolean {
        return imageUri != null
                && spotName.isNotBlank()
                && city.isNotBlank()
                && country.isNotBlank()
                && difficulty > 0
                && surfBreaks.isNotBlank()
                && seasonStart != null
                && seasonEnd != null
    }
}

class NewSpotViewModel : ViewModel() {
    private val _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage: StateFlow<String?> = _uiMessage

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    fun sendSpot(context: Context, formSpot: SpotFormState) {
        viewModelScope.launch {
            try {
                var photoUri =""

                formSpot.imageUri?.let { uri ->
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val bytes = inputStream?.readBytes()

                    inputStream?.close()

                    if (bytes != null) {
                        val requestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())
                        val filePart = MultipartBody.Part.createFormData(
                            "image",
                            "spot_${System.currentTimeMillis()}.jpg",
                            requestBody
                        )
                        val uploadResponse = ApiClient.api.uploadImage(filePart)

                        if (uploadResponse.isSuccessful) {
                            photoUri = uploadResponse.body()?.photoUrl ?: ""
                        } else {
                            _uiMessage.value = "Failed to upload"
                            _isSuccess.value = false
                            return@launch
                        }
                    }
                }

                val spotRequest = SpotDetailsUi(
                    id = 0,
                    photoUrl = photoUri,
                    name = formSpot.spotName,
                    city = formSpot.city,
                    country = formSpot.country,
                    difficulty = formSpot.difficulty,
                    surfBreaks = formSpot.surfBreaks,
                    seasonStart = formSpot.seasonStart?.toString() ?: "",
                    seasonEnd = formSpot.seasonEnd?.toString() ?: ""
                )
                val response = ApiClient.api.addSpot(spotRequest)
                val jsonString = Gson().toJson(response)
                val json = JSONObject(jsonString)
                _uiMessage.value = json.optString("message") //spot added successfully
                _isSuccess.value = true

            } catch (e: retrofit2.HttpException) {
                val errorJson = e.response()?.errorBody()?.string()
                val parsedError = errorJson?.let { Json.decodeFromString<ApiError>(it) }
                _uiMessage.value = parsedError?.error //spot already exists
                _isSuccess.value = false

            } catch (e: Exception) {
                _uiMessage.value = e.message //server error
                _isSuccess.value = false
            }
        }
    }

    fun formatDateFromMillis(millis: Long): String {
        val date = Date(millis)
        val formatter = SimpleDateFormat("dd MMM", Locale.getDefault())
        return formatter.format(date)
    }
}