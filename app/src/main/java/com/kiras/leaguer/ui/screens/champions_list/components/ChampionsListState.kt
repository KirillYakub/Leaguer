package com.kiras.leaguer.ui.screens.champions_list.components

import com.kiras.leaguer.domain.model.champions.ChampionModel

data class ChampionsListState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val champions: List<ChampionModel> = emptyList(),
    val filteredChampions: List<ChampionModel> = emptyList(),
    val searchText: String = ""
)