package com.kiras.leaguer.ui.screens.champion_details.components

import com.kiras.leaguer.domain.model.champions.ChampionModel

data class ChampionDetailsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val champion: ChampionModel? = null
)
