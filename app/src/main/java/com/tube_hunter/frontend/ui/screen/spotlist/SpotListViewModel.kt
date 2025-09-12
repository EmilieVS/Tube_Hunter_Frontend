package com.tube_hunter.frontend.ui.screen.spotlist

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tube_hunter.frontend.data.api.ApiClient
import com.tube_hunter.frontend.ui.component.SpotDetailsUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    private val _selectedDifficulty = MutableStateFlow<Int?>(null)
    val selectedDifficulty: StateFlow<Int?> = _selectedDifficulty

    private val _selectedSurfBreak = MutableStateFlow<String?>(null)
    val selectedSurfBreak: StateFlow<String?> = _selectedSurfBreak

    val allCountries: StateFlow<List<String>> = _spots
        .map { spots -> spots.map { it.country }.distinct() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _countryQuery = MutableStateFlow("")
    val countryQuery: StateFlow<String> = _countryQuery

    private val _selectedCountry = MutableStateFlow<String?>(null)
    val selectedCountry: StateFlow<String?> = _selectedCountry

    val filteredCountries: StateFlow<List<String>> =
        combine(_countryQuery, allCountries) { query, countries ->
            if (query.isBlank()) {
                emptyList()
            } else {
                countries.filter { it.contains(query, ignoreCase = true) }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearch(query: String) {
        _countryQuery.value = query
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
        _selectedCountry.value = null
        _countryQuery.value = ""
    }

    val filteredSpots: StateFlow<List<SpotDetailsUi>> = combine(
        _spots, _selectedDifficulty, _selectedSurfBreak, _selectedCountry
    ) { spots, difficulty, surfBreak, country ->
        spots.filter { spot ->
            val matchDifficulty = difficulty?.let { spot.difficulty == it } ?: true
            val matchSurfBreak = surfBreak?.let { spot.surfBreaks.contains(it, ignoreCase = true) } ?: true
            val matchCountry = country?.let { spot.country.equals(it, ignoreCase = true) } ?: true
            matchDifficulty && matchSurfBreak && matchCountry
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}