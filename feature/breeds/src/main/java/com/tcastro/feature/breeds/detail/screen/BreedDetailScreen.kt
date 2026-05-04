package com.tcastro.feature.breeds.detail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.tcastro.core.ui.components.GenericErrorComponent
import com.tcastro.core.ui.components.GenericLoadingComponent
import com.tcastro.core.ui.theme.Dimen
import com.tcastro.core.ui.theme.SwordCatChallengeTheme
import com.tcastro.feature.breeds.detail.components.BreedInfoChipComponent
import com.tcastro.feature.breeds.detail.model.BreedDetailUiModel
import com.tcastro.feature.breeds.detail.state.BreedDetailState
import com.tcastro.feature.breeds.detail.viewmodel.BreedDetailViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun BreedDetailScreen(
    modifier: Modifier = Modifier,
    breedId:String,
    viewModel: BreedDetailViewModel = koinViewModel(
        key = breedId,
        parameters = { parametersOf(breedId) }
    ),
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is BreedDetailState.Loading -> GenericLoadingComponent()
        is BreedDetailState.Error -> GenericErrorComponent(title = state.message)
        is BreedDetailState.Success -> {
            BreedDetailContent(
                state = state,
                onBack = onBack,
                onFavouriteClick = viewModel::toggleFavourite,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun BreedDetailContent(
    state: BreedDetailState.Success,
    onBack: () -> Unit,
    onFavouriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val breed = state.breed

    Scaffold(
        bottomBar = {
            ButtonSectionComponent(
                breed.isFavourite,
                onFavouriteClick = onFavouriteClick
            )
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSectionComponent(
                imageUrl = breed.imageUrl,
                imageDescription = breed.name,
                onBackClick = onBack
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues)
                    .padding(horizontal = Dimen.Spacing.large)
            ) {
                Spacer(modifier = Modifier.height(Dimen.Spacing.large))

                HeaderSectionTitleComponent(breed.name,breed.origin)

                Spacer(modifier = Modifier.height(Dimen.Spacing.mediumPlus))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BreedInfoChipComponent(
                        icon = Icons.Outlined.Notifications,
                        label = "LIFESPAN",
                        value = breed.lifespan?:"-",
                        modifier = Modifier.weight(1f)
                    )

                    BreedInfoChipComponent(
                        icon = Icons.Outlined.Home,
                        label = "ORIGIN",
                        value = breed.origin,
                        modifier = Modifier.weight(1f)
                    )

                }

                Spacer(modifier = Modifier.height(Dimen.Spacing.mediumPlus))

                //region Temperament
                Text(
                    text = "Temperament",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(Dimen.Spacing.default))

                Text(
                    text = breed.temperament,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )

                Spacer(modifier = Modifier.height(Dimen.Spacing.mediumPlus))
                //endregion

                //region Description
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(Dimen.Spacing.default))

                Text(
                    text = breed.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
                //endregion

                Spacer(modifier = Modifier.height(Dimen.Spacing.large))
            }
        }

    }

}

@Composable
private fun HeaderSectionComponent(
    imageUrl: String?,
    imageDescription: String,
    onBackClick: () -> Unit
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
                contentDescription = imageDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
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


        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(16.dp)
                .size(36.dp)
                .clip(CircleShape)
                .align(Alignment.TopStart)
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun HeaderSectionTitleComponent(name: String, origin: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = "Origin: $origin",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}


@Composable
private fun ButtonSectionComponent(
    isFavourite: Boolean = false,
    onFavouriteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Button(
            onClick = onFavouriteClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Icon(
                imageVector = if (isFavourite) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Outlined.FavoriteBorder
                },
                contentDescription = null,
                modifier = Modifier.size(Dimen.Images.iconDefault)
            )
            Spacer(modifier = Modifier.width(Dimen.Spacing.default))
            Text(
                text = if (isFavourite) "Remove from favourites" else "Add to favourites",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun BreedDetailContentPreview() {
    val breed = BreedDetailUiModel(
        "aege",
        name = "Aegean",
        imageUrl = "",
        temperament = "chill",
        description = "dfoguhs pdhf shdg piub",
        origin = "asd sasd",
        isFavourite = true,
        lifespan = "1",
    )
    SwordCatChallengeTheme() {


        BreedDetailContent(
            state = BreedDetailState.Success(breed),
            onBack = {},
            onFavouriteClick = {},
            modifier = Modifier
        )
    }
}