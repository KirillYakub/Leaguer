package com.kiras.leaguer.ui.screens.champion_details.composable

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kiras.leaguer.data.repository.ChampionsApiResponseImpl
import com.kiras.leaguer.domain.model.champions.ChampionModel

@Composable
fun ChampionHeader(
    championModel: ChampionModel,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                text = championModel.name ?: "",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        supportingContent = {
            Text(text = championModel.tags?.firstOrNull() ?: "")
        },
        leadingContent = {
            AsyncImage(
                model = ChampionsApiResponseImpl.imageSquareUrl + "${championModel.name}.png",
                contentDescription = "Hero square image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.size(40.dp)
            )
        },
        trailingContent = {
            Text(
                text = championModel.title ?: "",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        )
    )
}