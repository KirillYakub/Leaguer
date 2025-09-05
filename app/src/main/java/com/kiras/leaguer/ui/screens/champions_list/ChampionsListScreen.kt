package com.kiras.leaguer.ui.screens.champions_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kiras.leaguer.data.repository.ChampionsApiResponseImpl
import com.kiras.leaguer.domain.model.champions.ChampionModel
import com.kiras.leaguer.ui.screens.champions_list.components.ChampionsListState
import com.kiras.leaguer.ui.common.ErrorScreen

@Composable
fun ChampionsListScreen(
    state: ChampionsListState,
    onSearchValueChange: (String) -> Unit,
    onRetry: () -> Unit,
    onChampionClick: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
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
            state.champions.isNotEmpty() -> {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Leaguer",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium
                        )
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings icon",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(onClick = onSettingsClick)
                        )
                    }
                    OutlinedTextField(
                        value = state.searchText,
                        onValueChange = onSearchValueChange,
                        placeholder = {
                            Text(text = "Search for champs")
                        },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search icon"
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 14.dp,
                                bottom = 20.dp
                            )
                    )
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(
                            state.filteredChampions
                                .ifEmpty { state.champions }
                                .filter { champ -> champ.name?.all { it.isLetter() } == true }
                        ) { champion ->
                            ChampionCard(
                                championModel = champion,
                                modifier = Modifier
                                    .animateItem()
                                    .clickable {
                                        champion.name?.let(onChampionClick)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChampionCard(
    championModel: ChampionModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            model = ChampionsApiResponseImpl.imageLoadingUrl + "${championModel.name}_0.jpg",
            contentDescription = "Hero image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .weight(0.3f)
                .height(180.dp)
        )
        Column(
            modifier = Modifier
                .weight(0.7f)
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = championModel.name ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = championModel.blurb ?: "",
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 24.sp
            )
        }
    }
}