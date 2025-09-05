package com.kiras.leaguer.ui.screens.settings.components.state

interface SettingsEvent {
    data class NotificationsChange(val isEnable: Boolean): SettingsEvent
    data class PasswordChange(val isEnable: Boolean, val password: String): SettingsEvent
}