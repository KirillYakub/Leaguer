package com.kiras.leaguer.util

import com.kiras.leaguer.domain.model.champions.ChampionModel

fun Map<String, ChampionModel>.toChampionsList(): List<ChampionModel> =
    values.toList()