package com.kiras.leaguer.data.di

import android.content.Context
import com.kiras.leaguer.data.DataStorePreferences
import com.kiras.leaguer.domain.repository.SettingsRepos
import com.kiras.leaguer.domain.use_case.SettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStorePreferences(
        @ApplicationContext context: Context
    ): SettingsRepos {
        return DataStorePreferences(context)
    }

    @Provides
    @Singleton
    fun provideSettingsUseCase(
        settingsRepos: SettingsRepos
    ): SettingsUseCase {
        return SettingsUseCase(settingsRepos)
    }
}