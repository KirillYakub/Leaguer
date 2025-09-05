package com.kiras.leaguer.domain.model.settings

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SettingsModel(
    val isNotificationEnabled: Boolean,
    val isPasswordEnabled: Boolean,
    val password: String?
)