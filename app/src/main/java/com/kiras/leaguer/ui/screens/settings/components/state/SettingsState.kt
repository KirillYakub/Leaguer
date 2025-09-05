package com.kiras.leaguer.ui.screens.settings.components.state

data class SettingsState(
    val isNotificationEnabled: Boolean = false,
    val isPasswordEnabled: Boolean = false,
    val isPasswordCorrect: Boolean = true,
    val password: String = ""
)