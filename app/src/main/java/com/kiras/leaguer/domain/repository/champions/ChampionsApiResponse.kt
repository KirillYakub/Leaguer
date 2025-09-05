package com.kiras.leaguer.domain.repository.champions

import com.kiras.leaguer.domain.model.champions.ChampionResponseModel
import com.skydoves.sandwich.ApiResponse

interface ChampionsApiResponse {
    suspend fun getAllChampions(): ApiResponse<ChampionResponseModel>
    suspend fun getChampionByName(name: String): ApiResponse<ChampionResponseModel>
}