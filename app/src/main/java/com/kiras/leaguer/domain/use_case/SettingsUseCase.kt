package com.kiras.leaguer.domain.use_case

import com.kiras.leaguer.domain.model.settings.SettingsModel
import com.kiras.leaguer.domain.repository.SettingsRepos
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter

@OptIn(ExperimentalStdlibApi::class)
class SettingsUseCase(private val settingsRepos: SettingsRepos) {

    private val moshi = Moshi.Builder().build()
    private val settingsModelJsonAdapter = moshi.adapter<SettingsModel>()

    suspend fun writePasswordSettings(settingsModel: SettingsModel) {
        val strJson = settingsModelJsonAdapter.toJson(settingsModel)
        settingsRepos.writeSettingsDataAsJson(strJson)
    }

    suspend fun readPasswordSettings(): SettingsModel {
        val strAsJson = settingsRepos.readSettingsDataAsJson()
        return strAsJson?.let { settingsModelJsonAdapter.fromJson(it) }
            ?: SettingsModel(
                isNotificationEnabled = false,
                isPasswordEnabled = false,
                password = null
            )
    }
}