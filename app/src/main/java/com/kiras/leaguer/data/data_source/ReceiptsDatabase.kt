package com.kiras.leaguer.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kiras.leaguer.data.data_source.dao.GetChampionDao
import com.kiras.leaguer.data.data_source.dao.InsertChampionDao

@Database(entities = [ChampionEntity::class], version = 1)
abstract class ChampionsDatabase : RoomDatabase() {
    abstract fun getChampionDao(): GetChampionDao
    abstract fun insertChampionDao(): InsertChampionDao
}