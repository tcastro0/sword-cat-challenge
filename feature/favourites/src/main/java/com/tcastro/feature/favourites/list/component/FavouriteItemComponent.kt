package com.tcastro.feature.favourites.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tcastro.core.ui.components.FavouriteButtonComponent
import com.tcastro.core.ui.theme.Dimen
import com.tcastro.core.ui.theme.SwordCatChallengeTheme
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
            .testTag("favourite_item")
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(Dimen.Spacing.medium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimen.Elevation.small)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimen.Spacing.default)
        ) {

            AsyncImage(
                model = favourite.breedImageUrl,
                contentDescription = favourite.breedName,
                modifier = Modifier

                    .size(80.dp)
                    .clip(RoundedCornerShape(Dimen.Spacing.small)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f),
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

            FavouriteButtonComponent(
                isFavourite = true,
                onClick = onRemove,
                modifier = Modifier
                    .padding(Dimen.Spacing.default)
                    .padding(end = Dimen.Spacing.medium),
                size = Dimen.Images.iconMedium,
                iconSize = Dimen.Images.iconDefault,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer
            )

        }

    }
}



@Preview(showBackground = true)
@Composable
fun FavouriteItemComponentPreview() {
    SwordCatChallengeTheme {
        FavouriteItemComponent(
            favourite = FavouriteUiModel(
                breedId = "1",
                breedName = "aegean",
                breedImageUrl = null,
                breedLifeSpan = "1-2",
                breedOrigin = "adsiuhasf"
            ),
            onClick = {},
            onRemove = {},
            modifier = Modifier
        )
    }
}