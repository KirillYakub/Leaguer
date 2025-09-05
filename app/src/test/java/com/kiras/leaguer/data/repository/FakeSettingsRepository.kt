package com.kiras.leaguer.data.repository

import com.kiras.leaguer.domain.repository.SettingsRepos

class FakeSettingsRepository : SettingsRepos {

    private var settingsDataAsJsonString: String? = null

    override suspend fun writeSettingsDataAsJson(string: String) {
        settingsDataAsJsonString = string
    }

    override suspend fun readSettingsDataAsJson(): String? {
        return settingsDataAsJsonString
    }
}