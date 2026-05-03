package com.tcastro.feature.breeds.detail.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tcastro.core.ui.components.GenericErrorComponent
import com.tcastro.core.ui.components.GenericLoadingComponent
import com.tcastro.feature.breeds.detail.state.BreedDetailState
import com.tcastro.feature.breeds.detail.viewmodel.BreedDetailViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun BreedDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: BreedDetailViewModel = koinViewModel(),
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
                onFavouriteClick = {},
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
    Column(
        modifier=modifier
    ){
        Text(breed.name)

    }

}