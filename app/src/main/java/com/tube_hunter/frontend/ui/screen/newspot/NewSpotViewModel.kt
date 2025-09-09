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
                    // 1. Ouvrir l'image sélectionnée : lecture des données depuis la gallerie
                    val inputStream = context.contentResolver.openInputStream(uri)

                    // 2. Lire les octets de l'image et récupérer l'image elle même et non plus un pointeur
                    val bytes = inputStream?.readBytes()

                    // 3. Fermer le flux ouvert (pour libérer les ressources)
                    inputStream?.close()

                    // 4. Si on a bien récupéré les octets
                    if (bytes != null) {
                        // Transformer les octets en "RequestBody" que Retrofit va envoyer
                        val requestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())

                        // Créer la partie multipart et un nom de fichier unique
                        val filePart = MultipartBody.Part.createFormData(
                            "image", // clé attendue par le back
                            "spot_${System.currentTimeMillis()}.jpg", // nom de fichier
                            requestBody
                        )
                        // 5. Envoyer l'image au serveur
                        val uploadResponse = ApiClient.api.uploadImage(filePart)

                        // 6. Si l'upload a réussi, on récupère l'URL envoyée par le back
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



