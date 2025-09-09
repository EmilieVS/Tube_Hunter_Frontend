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

    private val _selectedDifficulty = MutableStateFlow<Int?>(null)
    val selectedDifficulty : StateFlow<Int?> = _selectedDifficulty

    private val _selectedSurfBreak = MutableStateFlow<String?>(null)
    val selectedSurfBreak : StateFlow<String?> = _selectedSurfBreak

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
                        id = welcome.id,
                        photoUrl = welcome.photoUrl,
                        name = welcome.name,
                        city = welcome.city,
                        country = welcome.country,
                        difficulty = welcome.difficulty,
                        surfBreaks = welcome.surfBreaks,
                        seasonStart = welcome.seasonStart,
                        seasonEnd = welcome.seasonEnd
                    )
                }
            } catch (e: Exception) {
                Log.e("API_RESPONSE", "Error during request", e)
                e.printStackTrace()
            }
        }
    }
        fun setDifficulty(level: Int?) {
            _selectedDifficulty.value = level
        }

        fun setSurfBreak(type: String?) {
            _selectedSurfBreak.value = type
        }

        fun clearFilters() {
            _selectedDifficulty.value = null
            _selectedSurfBreak.value = null
        }

    }




