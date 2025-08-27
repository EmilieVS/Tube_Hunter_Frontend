package com.tube_hunter.frontend.ui.screen.addspot

import android.net.Uri

data class SpotFormState(
    val spotName: String = "",
    val location: String = "",
    val imageUri: Uri? = null,
    val difficulty: String? = null,
    val surfBreaks: List<String> = emptyList(),
    val seasonStart: Long? = null,
    val seasonEnd: Long? = null,
) {
    fun isValid(): Boolean {
        return spotName.isNotBlank()
                && location.isNotBlank()
                && difficulty != null
                && surfBreaks.isNotEmpty()
                && seasonStart != null
                && seasonEnd != null
    }
}