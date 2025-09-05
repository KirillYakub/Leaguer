package com.kiras.leaguer.domain.model.champions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageModel(
    @SerialName("full")
    val full: String? = "",
    @SerialName("group")
    val group: String? = "",
    @SerialName("sprite")
    val sprite: String? = ""
)