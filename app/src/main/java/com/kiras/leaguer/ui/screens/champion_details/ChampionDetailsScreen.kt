package com.kiras.leaguer.ui.screens.champion_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kiras.leaguer.data.repository.ChampionsApiResponseImpl
import com.kiras.leaguer.domain.model.champions.ChampionModel
import com.kiras.leaguer.ui.screens.champion_details.components.ChampionDetailsState
import com.kiras.leaguer.ui.screens.champion_details.composable.ChampionHeader
import com.kiras.leaguer.ui.screens.champion_details.composable.ChampionLore
import com.kiras.leaguer.ui.screens.champion_details.composable.ChampionPassive
import com.kiras.leaguer.ui.screens.champion_details.composable.ChampionSpell
import com.kiras.leaguer.ui.common.ErrorScreen

@Composable
fun ChampionDetailsScreen(
    state: ChampionDetailsState,
    onRetry: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    strokeWidth = 5.dp
                )
            }
            state.error != null -> {
                ErrorScreen(
                    error = state.error,
                    onRetry = onRetry
                )
            }
            state.champion != null -> {
                ChampionDetailsList(champion = state.champion)
            }
        }
    }
}

@Composable
fun ChampionDetailsList(champion: ChampionModel) {
    LazyColumn {
        item {
            AsyncImage(
                model = ChampionsApiResponseImpl.imageSplashUrl + "${champion.name}_0.jpg",
                contentDescription = "Hero splash image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            ChampionHeader(
                championModel = champion,
                modifier = Modifier.padding(
                    horizontal = 10.dp,
                    vertical = 6.dp
                )
            )
            ChampionLore(
                lore = champion.lore ?: "",
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 6.dp
                )
            )
            champion.passive?.let { passive ->
                ChampionPassive(
                    passive = passive,
                    modifier = Modifier.padding(
                        horizontal = 6.dp,
                        vertical = 10.dp
                    )
                )
            }
            champion.spells?.forEach { spell ->
                ChampionSpell(
                    spell = spell,
                    modifier = Modifier.padding(
                        horizontal = 6.dp,
                        vertical = 10.dp
                    )
                )
            }
        }
    }
}