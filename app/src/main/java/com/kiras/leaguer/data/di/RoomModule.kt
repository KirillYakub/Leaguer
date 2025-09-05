package com.kiras.leaguer.data.di

import android.content.Context
import androidx.room.Room
import com.kiras.leaguer.data.data_source.ChampionsDatabase
import com.kiras.leaguer.data.repository.GetChampionReposImpl
import com.kiras.leaguer.data.repository.InsertChampionReposImpl
import com.kiras.leaguer.domain.repository.champions.GetChampionRepos
import com.kiras.leaguer.domain.repository.champions.InsertChampionRepos
import com.kiras.leaguer.util.Constants.CHAMPS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideReceiptsDatabase(
        @ApplicationContext context: Context
    ): ChampionsDatabase {
        return Room.databaseBuilder(
            context,
            ChampionsDatabase::class.java,
            CHAMPS_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideGetChampionRepos(
        championsDatabase: ChampionsDatabase
    ): GetChampionRepos {
        return GetChampionReposImpl(
            getChampionDao = championsDatabase.getChampionDao()
        )
    }

    @Provides
    @Singleton
    fun provideInsertChampionRepos(
        championsDatabase: ChampionsDatabase
    ): InsertChampionRepos {
        return InsertChampionReposImpl(
            insertChampionDao = championsDatabase.insertChampionDao()
        )
    }
}