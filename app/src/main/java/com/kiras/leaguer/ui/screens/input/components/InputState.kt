package com.kiras.leaguer.ui.screens.input.components

data class InputState(
    val isLoading: Boolean = true,
    val isPasswordEnabled: Boolean = false,
    val isInputPasswordCorrect: Boolean = false,
    val correctPassword: String = "",
    val inputPassword: String = ""
)
