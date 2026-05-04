package com.tcastro.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tcastro.core.ui.theme.SwordCatChallengeTheme


@Composable
fun FavouriteButtonComponent(
    isFavourite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    iconSize: Dp = 20.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor)
    ) {
        Icon(
            imageVector = if (isFavourite) {
                Icons.Filled.Favorite
            } else {
                Icons.Outlined.FavoriteBorder
            },
            contentDescription = if (isFavourite) {
                "Remove from favourites"
            } else {
                "Add to favourites"
            },
            tint = if (isFavourite) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.outline
            },
            modifier = Modifier.size(iconSize)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavouriteButtonComponentPreview() {
    SwordCatChallengeTheme() {

        FavouriteButtonComponent(
            isFavourite = false,
            onClick = {},
            modifier = Modifier,
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun FavouriteButtonComponentTruePreview() {
    SwordCatChallengeTheme() {
        FavouriteButtonComponent(
            isFavourite = true,
            onClick = {},
            modifier = Modifier,
        )

    }
}
