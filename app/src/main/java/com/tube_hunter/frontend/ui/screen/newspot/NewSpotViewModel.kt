package com.tube_hunter.frontend.ui.screen.newspot

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tube_hunter.frontend.data.api.ApiClient
import com.tube_hunter.frontend.ui.component.SpotDetailsUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
                val result = ApiClient.api.addSpot(spotRequest)
                Log.d("API_RESPONSE", "Response: $result")

            } catch (e: Exception) {
                Log.e("API_RESPONSE", "Error during request", e )
                e.printStackTrace()
            }
        }
    }
}
