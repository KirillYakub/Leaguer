package com.kiras.leaguer.data.data_source.dao

import androidx.room.Dao
import androidx.room.Query
import com.kiras.leaguer.data.data_source.ChampionEntity

@Dao
interface GetChampionDao {

    @Query("SELECT * FROM champions_table")
    fun getAllReceipts(): List<ChampionEntity>
}