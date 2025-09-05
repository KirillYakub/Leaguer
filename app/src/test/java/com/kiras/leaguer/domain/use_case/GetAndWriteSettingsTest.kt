package com.kiras.leaguer.domain.use_case

import com.kiras.leaguer.data.repository.FakeSettingsRepository
import com.kiras.leaguer.domain.model.settings.SettingsModel
import com.kiras.leaguer.domain.repository.SettingsRepos
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalStdlibApi::class)
class GetAndWriteSettingsTest {

    private val moshi = Moshi.Builder().build()
    private val settingsModelJsonAdapter = moshi.adapter<SettingsModel>()

    private lateinit var getSettingsRepos: SettingsRepos
    private lateinit var settingsModel: SettingsModel

    @Before
    fun setUp() {
        getSettingsRepos = FakeSettingsRepository()
        settingsModel = SettingsModel(
            isNotificationEnabled = true,
            isPasswordEnabled = true,
            password = "4321"
        )
    }

    @Test
    fun `Settings compare after writing and reading it as json`() {
        val strJson = settingsModelJsonAdapter.toJson(settingsModel)
        runBlocking {
            // Writing settings as json
            getSettingsRepos.writeSettingsDataAsJson(strJson)

            // Reading settings from json
            val settingsModelFromJson = getSettingsRepos.readSettingsDataAsJson()
                ?.let { settingsModelJsonAdapter.fromJson(it) }
                ?: throw IllegalArgumentException("Settings data can not be null")

            assertEquals(settingsModel, settingsModelFromJson)
        }
    }
}