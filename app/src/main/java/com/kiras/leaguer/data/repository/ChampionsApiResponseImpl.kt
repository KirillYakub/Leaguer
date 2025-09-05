package com.kiras.leaguer.data.repository

import com.kiras.leaguer.util.Constants.BASE_URL_ENDPOINT
import com.kiras.leaguer.domain.model.champions.ChampionResponseModel
import com.kiras.leaguer.domain.repository.champions.ChampionsApiResponse
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import io.ktor.client.HttpClient

class ChampionsApiResponseImpl(
    private val httpClient: HttpClient
) : ChampionsApiResponse {

    companion object {
        const val baseUrl = "https://ddragon.leagueoflegends.com/cdn/14.23.1/data/en_US/"

        const val imageSplashUrl = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"
        const val imageLoadingUrl = "https://ddragon.leagueoflegends.com/cdn/img/champion/loading/"
        const val imageSquareUrl = "https://ddragon.leagueoflegends.com/cdn/9.19.1/img/champion/"
        const val imagePassiveUrl = "https://ddragon.leagueoflegends.com/cdn/9.19.1/img/passive/"
        const val imageAbilityUrl = "https://ddragon.leagueoflegends.com/cdn/9.19.1/img/spell/"
    }

    override suspend fun getAllChampions(): ApiResponse<ChampionResponseModel> {
       return httpClient.getApiResponse("$BASE_URL_ENDPOINT.json")
    }

    override suspend fun getChampionByName(name: String): ApiResponse<ChampionResponseModel> {
        return httpClient.getApiResponse("$BASE_URL_ENDPOINT/$name.json")
    }
}