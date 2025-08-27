package com.tube_hunter.frontend.ui.component

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotDetailsUi(
    val id: String,
    val photoUrl: String,
    val name: String,
    val location: String,
    val difficulty: Int,
    val surfBreak: String,
    val seasonBegins: String,
    val seasonEnds: String,
) : Parcelable