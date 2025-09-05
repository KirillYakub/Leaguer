package com.kiras.leaguer.data.data_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.kiras.leaguer.data.data_source.ChampionEntity

@Dao
interface InsertChampionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChampion(champion: ChampionEntity)
}