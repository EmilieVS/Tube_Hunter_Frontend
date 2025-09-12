package com.tube_hunter.frontend.ui.component

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotDetailsUi(
    val id: Long,
    val photoUrl: String,
    val name: String,
    val city: String,
    val country: String,
    val difficulty: Int,
    val surfBreaks: String,
    val seasonStart: String,
    val seasonEnd: String,
) : Parcelable