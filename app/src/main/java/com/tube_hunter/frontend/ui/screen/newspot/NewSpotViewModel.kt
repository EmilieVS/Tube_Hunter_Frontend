package com.tube_hunter.frontend.ui.screen.newspot

import ApiError
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.tube_hunter.frontend.data.api.ApiClient
import com.tube_hunter.frontend.ui.component.SpotDetailsUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.json.JSONObject

data class SpotFormState(
    val imageUrl: String = "",
    val spotName: String = "",
    val city: String = "",
    val country: String = "",
    val difficulty: Int = 0,
    val surfBreaks: String = "",
    val seasonStart: Long? = null,
    val seasonEnd: Long? = null
) {
    fun isValid(): Boolean {
        return imageUrl.isNotBlank()
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

    fun sendSpot(formSpot: SpotFormState) {
        viewModelScope.launch {
            try {
                val spotRequest = SpotDetailsUi(
                    id = 0,
                    photoUrl = formSpot.imageUrl,
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
                _uiMessage.value = json.optString("message") //spot added successfuly
                _isSuccess.value = true

            } catch (e: retrofit2.HttpException) {
                val errorJson = e.response()?.errorBody()?.string()
                val parsedError = errorJson?.let { Json.decodeFromString<ApiError>(it) }
                _uiMessage.value = parsedError?.error //spot already exists
                _isSuccess.value = false

            } catch (e: Exception) {
                _uiMessage.value = e.message //servor error
                _isSuccess.value = false
            }
        }
    }
}

