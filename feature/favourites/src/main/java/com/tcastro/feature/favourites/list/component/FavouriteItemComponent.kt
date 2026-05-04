package com.tcastro.feature.favourites.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tcastro.core.ui.theme.Dimen
import com.tcastro.feature.favourites.list.model.FavouriteUiModel



@Composable
fun FavouriteItemComponent(
    favourite: FavouriteUiModel,
    onClick: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(Dimen.Spacing.medium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimen.Elevation.small)
    ) {

        Column {
            Box {
                AsyncImage(
                    model = favourite.breedImageUrl,
                    contentDescription = favourite.breedName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimen.Images.xLarge),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = onRemove,
                    modifier = Modifier
                        .padding(Dimen.Spacing.smallPlus)
                        .size(Dimen.Images.iconMedium)
                        .clip(CircleShape)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Remove from favourites",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(Dimen.Images.iconDefault)
                    )
                }
            }


            Column(
                modifier = Modifier.padding(
                    horizontal = Dimen.Spacing.defaultPlus,
                    vertical = Dimen.Spacing.default
                ),
                verticalArrangement = Arrangement.spacedBy(Dimen.Spacing.small)
            ) {
                Text(
                    text = favourite.breedName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(Dimen.Spacing.smallPlus)
                    )
                    Text(
                        text = favourite.breedOrigin,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

    }
}