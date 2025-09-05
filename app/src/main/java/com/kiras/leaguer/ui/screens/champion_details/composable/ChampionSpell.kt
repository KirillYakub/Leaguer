package com.kiras.leaguer.ui.screens.champion_details.composable

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kiras.leaguer.data.repository.ChampionsApiResponseImpl
import com.kiras.leaguer.domain.model.champions.SpellModel

@Composable
fun ChampionSpell(
    spell: SpellModel,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(text = spell.name ?: "")
        },
        supportingContent = {
            Text(text = spell.description ?: "")
        },
        leadingContent = {
            AsyncImage(
                model = ChampionsApiResponseImpl.imageAbilityUrl + "${spell.id}.png",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        )
    )
}
