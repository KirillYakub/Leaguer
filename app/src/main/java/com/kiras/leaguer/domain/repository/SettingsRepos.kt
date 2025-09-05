package com.kiras.leaguer.domain.repository

interface SettingsRepos {
    suspend fun writeSettingsDataAsJson(string: String)
    suspend fun readSettingsDataAsJson(): String?
}