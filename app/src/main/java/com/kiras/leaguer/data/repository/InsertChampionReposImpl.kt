package com.kiras.leaguer.data.repository

import com.kiras.leaguer.data.data_source.ChampionEntity
import com.kiras.leaguer.data.data_source.dao.InsertChampionDao
import com.kiras.leaguer.domain.repository.champions.InsertChampionRepos

class InsertChampionReposImpl(
    private val insertChampionDao: InsertChampionDao
): InsertChampionRepos {

    override suspend fun insertChampion(champion: String) {
        insertChampionDao.insertChampion(
            ChampionEntity(championName = champion)
        )
    }
}