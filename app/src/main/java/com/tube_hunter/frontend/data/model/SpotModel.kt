package com.tube_hunter.frontend.data.model

import kotlinx.serialization.*

@Serializable
data class Welcome (
    @SerialName("id")
    val id: Long,

    @SerialName("photo_url")
    val photoUrl: String,

    @SerialName("name")
    val name: String,

    @SerialName("city")
    val city: String,

    @SerialName("country")
    val country: String,

    @SerialName("difficulty")
    val difficulty: Int,

    @SerialName("surf_breaks")
    val surfBreaks: String,

    @SerialName("season_start")
    val seasonStart: String,

    @SerialName("season_end")
    val seasonEnd: String,
)