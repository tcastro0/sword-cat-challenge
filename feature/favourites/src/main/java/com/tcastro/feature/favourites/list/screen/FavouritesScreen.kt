package com.tcastro.feature.favourites.list.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tcastro.core.ui.components.GenericEmptyComponent
import com.tcastro.core.ui.components.GenericErrorComponent
import com.tcastro.core.ui.components.GenericLoadingComponent
import com.tcastro.core.ui.components.TitleBarComponent
import com.tcastro.core.ui.theme.Dimen
import com.tcastro.feature.favourites.list.component.AverageLifespanCardComponent
import com.tcastro.feature.favourites.list.component.FavouriteItemComponent
import com.tcastro.feature.favourites.list.model.FavouriteUiModel
import com.tcastro.feature.favourites.list.state.FavouritesState
import com.tcastro.feature.favourites.list.viewmodel.FavouritesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavouritesViewModel = koinViewModel(),
    onItemClick: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = modifier.fillMaxSize()) {
        TitleBarComponent("Favourites")

        when (val state = uiState) {
            is FavouritesState.Loading -> GenericLoadingComponent()
            is FavouritesState.Error -> GenericErrorComponent(
                modifier = modifier,
                title = state.message
            )

            is FavouritesState.Success -> {
                if (state.favourites.isEmpty()) {
                    GenericEmptyComponent(
                        modifier = modifier,
                        title = "No favourites yet"
                    )
                } else {

                    FavouriteListSection(
                        state.favourites,
                        state.averageLifespan,
                        viewModel::removeFavourite,
                        onItemClick
                    )
                }
            }
        }
    }


}

@Composable
private fun FavouriteListSection(
    favourites: List<FavouriteUiModel>,
    averageLifespan: Double,
    removeClick: (FavouriteUiModel) -> Unit,
    onItemClick: (String) -> Unit,
) {

    LazyColumn(
        contentPadding = PaddingValues(Dimen.Spacing.medium),
        verticalArrangement = Arrangement.spacedBy(Dimen.Spacing.default),
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            AverageLifespanCardComponent(
                favouriteCount = favourites.size,
                averageLifespan = averageLifespan,
            )
        }

        items(
            items = favourites,
            key = { it.breedId }
        ) { favourite ->
            FavouriteItemComponent(
                favourite = favourite,
                onClick = { onItemClick(favourite.breedId) },
                onRemove = { removeClick(favourite) }
            )
        }

    }

}




