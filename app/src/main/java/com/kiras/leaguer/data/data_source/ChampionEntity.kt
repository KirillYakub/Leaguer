package com.kiras.leaguer.data.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kiras.leaguer.util.Constants.CHAMPS_TABLE_NAME

@Entity(tableName = CHAMPS_TABLE_NAME)
data class ChampionEntity (
    @PrimaryKey
    val id: Int = 0,
    val championName: String = ""
)