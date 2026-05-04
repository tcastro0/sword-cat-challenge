package com.tcastro.feature.breeds.list.component

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.tcastro.core.ui.theme.Dimen
import com.tcastro.core.ui.theme.SwordCatChallengeTheme

@Composable
fun BreedListItemComponent(
    modifier: Modifier = Modifier,
    id: String,
    name: String,
    lifespan: String?,
    imageUrl: String?,
    onClick: (String) -> Unit,
    isFavourite: Boolean = false,
    onFavouriteClick: (() -> Unit)? = null,
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(id) }),
        shape = RoundedCornerShape(Dimen.Dimensions.medium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimen.Elevation.small)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimen.Images.xxLarge)
        ) {
            if (!imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimen.Images.xxLarge),
                    contentScale = ContentScale.Crop,
                )

            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .size(Dimen.Dimensions.xxlarge)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                val icon = if(isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                val iconTint = if(isFavourite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                IconButton(
                    onClick = { onFavouriteClick?.invoke()},
                    modifier = Modifier
                        .padding(Dimen.Spacing.medium)
                        .size(Dimen.Images.iconDefaultPlus)
                        .clip(CircleShape)
                        .background(color = Color.White.copy(alpha = 0.6f))
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "set favourite",
                        tint = iconTint,
                        modifier = Modifier.size(Dimen.Images.iconMedium)
                    )
                }
            }

            BreedInfoSection(
                Modifier.align(Alignment.BottomStart),
                name,
                lifespan
            )
        }

    }


}

@Composable
private fun BreedInfoSection(
    modifier: Modifier,
    name: String,
    lifespan: String?
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White.copy(alpha = 0.6f),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = Dimen.Dimensions.medium,
                    bottomEnd = Dimen.Dimensions.medium
                    )
            )
        .padding(
            horizontal = Dimen.Dimensions.small,
            vertical = Dimen.Dimensions.small
        ),
        verticalArrangement = Arrangement.spacedBy(Dimen.Spacing.small)
    ) {

        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimen.Dimensions.extraSmall)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(Dimen.Dimensions.smallPlus)
            )
            Text(
                text = lifespan?:"-",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BreedListItemComponentPreview() {
    SwordCatChallengeTheme() {
        BreedListItemComponent(
            id = "1",
            name = "persian",
            lifespan = "20 anos",
            imageUrl = "teste",
            onClick = {},
            onFavouriteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BreedListItemComponentNoImagePreview() {
    SwordCatChallengeTheme() {
        BreedListItemComponent(
            id = "1",
            name = "persian iyufyig ygyuggyuhj",
            lifespan = "20 anos",
            imageUrl = null,
            onClick = {},
            onFavouriteClick = {}
        )
    }
}