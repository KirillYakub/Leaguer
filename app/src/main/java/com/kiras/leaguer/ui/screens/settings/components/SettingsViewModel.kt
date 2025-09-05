package com.kiras.leaguer.ui.screens.settings.components

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.kiras.leaguer.domain.model.settings.SettingsModel
import com.kiras.leaguer.domain.use_case.SettingsUseCase
import com.kiras.leaguer.framework.worker.NotificationsWorker
import com.kiras.leaguer.ui.screens.settings.components.state.SettingsEvent
import com.kiras.leaguer.ui.screens.settings.components.state.SettingsState
import com.kiras.leaguer.util.Constants.NOTIFICATIONS_WORK_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
    private val workManager: WorkManager
) : ViewModel() {

    var settingsState by mutableStateOf(SettingsState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsUseCase.readPasswordSettings().apply {
                settingsState = SettingsState(
                    isNotificationEnabled = isNotificationEnabled,
                    isPasswordEnabled = isPasswordEnabled,
                    password = password ?: ""
                )
            }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.NotificationsChange -> {
                settingsState = settingsState.copy(isNotificationEnabled = event.isEnable)
                enableNotificationsWorkManager()
            }
            is SettingsEvent.PasswordChange -> {
                settingsState = settingsState.copy(
                    isPasswordEnabled = event.isEnable,
                    isPasswordCorrect = event.password.length == 4,
                    password = event.password
                )
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            settingsState.apply {
                settingsUseCase.writePasswordSettings(
                    settingsModel = SettingsModel(
                        isNotificationEnabled = isNotificationEnabled,
                        isPasswordEnabled = isPasswordEnabled,
                        password = password.takeIf { it.length == 4 } ?: ""
                    )
                )
            }
        }
    }

    private fun enableNotificationsWorkManager() {
        if(settingsState.isNotificationEnabled) {
            val workRequest = PeriodicWorkRequest
                .Builder(
                    NotificationsWorker::class.java,
                    15,
                    TimeUnit.MINUTES
                )
                .setInitialDelay(30, TimeUnit.MINUTES)
                .build()
            workManager.enqueueUniquePeriodicWork(
                NOTIFICATIONS_WORK_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )
        }
        else
            workManager.cancelUniqueWork(NOTIFICATIONS_WORK_NAME)
    }
}