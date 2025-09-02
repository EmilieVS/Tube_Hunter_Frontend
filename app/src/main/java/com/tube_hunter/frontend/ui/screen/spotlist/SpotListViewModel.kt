package com.tube_hunter.frontend.ui.screen.spotlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tube_hunter.frontend.data.api.ApiClient
import com.tube_hunter.frontend.ui.component.SpotDetailsUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SpotListViewModel : ViewModel() {
    private val _spots = MutableStateFlow<List<SpotDetailsUi>>(emptyList())
    val spots: StateFlow<List<SpotDetailsUi>> = _spots

    init {
        loadSpots()
    }

    private fun loadSpots() {
        viewModelScope.launch {
            try {
                val result = ApiClient.api.getAllSpots()
                Log.d("API_RESPONSE", "Response: $result")

                // Mapping entre Welcome (backend) et SpotDetailsUi (UI)
                _spots.value = result.map { welcome ->
                    SpotDetailsUi(
                        id = welcome.id.toString(),
                        photoUrl = welcome.photoUrl,
                        name = welcome.name,
                        country = welcome.location.country,
                        city = welcome.location.city,
                        difficulty = welcome.difficulty,
                        surfBreaks = welcome.surfBreaks,
                        seasonStart = welcome.seasonStart,
                        seasonEnd = welcome.seasonEnd
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
