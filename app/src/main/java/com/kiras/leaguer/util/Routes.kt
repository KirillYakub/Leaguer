package com.kiras.leaguer.util

import kotlinx.serialization.Serializable

@Serializable
data object Input

@Serializable
data object Settings

@Serializable
data object ChampionsList

@Serializable
data class ChampionsDetails(val name: String)