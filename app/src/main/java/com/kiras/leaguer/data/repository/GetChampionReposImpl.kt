package com.kiras.leaguer.data.repository

import com.kiras.leaguer.data.data_source.dao.GetChampionDao
import com.kiras.leaguer.domain.repository.champions.GetChampionRepos

class GetChampionReposImpl(
    private val getChampionDao: GetChampionDao
): GetChampionRepos {

    override fun getAllChampions(): List<String> {
        return getChampionDao.getAllReceipts().map { it.championName }
    }
}