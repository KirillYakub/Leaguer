package com.kiras.leaguer.domain.repository.champions

interface InsertChampionRepos {
    suspend fun insertChampion(champion: String)
}