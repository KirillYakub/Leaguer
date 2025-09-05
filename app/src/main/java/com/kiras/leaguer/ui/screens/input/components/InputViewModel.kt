package com.kiras.leaguer.ui.screens.input.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiras.leaguer.domain.use_case.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(InputState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsUseCase.readPasswordSettings().apply {
                delay(600)
                _state.update { inputState ->
                    inputState.copy(
                        isLoading = false,
                        isPasswordEnabled = isPasswordEnabled,
                        correctPassword = password ?: ""
                    )
                }
            }
        }
    }

    fun onPasswordChange(password: String) {
        _state.update { inputState ->
            inputState.copy(
                inputPassword = password,
                isInputPasswordCorrect = inputState.correctPassword == password
            )
        }
    }
}