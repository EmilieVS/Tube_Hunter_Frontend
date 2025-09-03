package com.tube_hunter.frontend.ui.component

import android.os.Parcelable
import com.tube_hunter.frontend.data.model.Location
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotDetailsUi(
    val id: String,
    val photoUrl: String,
    val name: String,
    val country: String,
    val city: String,
    val difficulty: Int,
    val surfBreaks: String,
    val seasonStart: String,
    val seasonEnd: String,
) : Parcelable