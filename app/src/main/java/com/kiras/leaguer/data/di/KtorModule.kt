package com.kiras.leaguer.data.di

import com.kiras.leaguer.util.Constants.BASE_URL
import com.kiras.leaguer.data.repository.ChampionsApiResponseImpl
import com.kiras.leaguer.domain.repository.champions.ChampionsApiResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KtorModule {

    @Provides
    @Singleton
    fun provideChampionsApiResponse(): ChampionsApiResponse {
        val client = HttpClient(Android) {
            defaultRequest {
                url(BASE_URL)
                header(HttpHeaders.ContentType, "application/json")
            }
            install(Logging)
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
        return ChampionsApiResponseImpl(httpClient = client)
    }
}