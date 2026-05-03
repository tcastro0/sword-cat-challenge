package com.tcastro.feature.breeds.list.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.tcastro.core.ui.components.GenericEmptyComponent
import com.tcastro.core.ui.components.GenericErrorComponent
import com.tcastro.core.ui.components.GenericLoadingComponent
import com.tcastro.core.ui.components.SearchFieldComponent
import com.tcastro.core.ui.theme.Dimen
import com.tcastro.core.ui.theme.SwordCatChallengeTheme
import com.tcastro.feature.breeds.list.component.BreedListItemComponent
import com.tcastro.feature.breeds.list.model.BreedUIModel
import com.tcastro.feature.breeds.list.state.BreedListState
import com.tcastro.feature.breeds.list.state.SearchState
import com.tcastro.feature.breeds.list.viewmodel.BreedListViewModel
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedListScreen(
    modifier: Modifier = Modifier,
    viewModel: BreedListViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val breedsState = viewModel.breeds.collectAsLazyPagingItems()

    BreedListScreenContent(
        modifier,
        uiState,
        breedsState,
        viewModel::onSearchQueryChanged,
        viewModel::onBreedClick
    )

}

@Composable
fun BreedListScreenContent(
    modifier: Modifier,
    uiState: BreedListState,
    breedsState: LazyPagingItems<BreedUIModel>,
    onSearchQueryChanged: (String) -> Unit,
    onBreedClick: (BreedUIModel) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        SearchFieldComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            query = uiState.searchQuery,
            onQueryChange = onSearchQueryChanged,
            placeholderText = "Search ..."
        )

        if (uiState.searchQuery.isEmpty()) {
            when (breedsState.loadState.refresh) {
                is LoadState.Loading -> GenericLoadingComponent()
                is LoadState.Error -> GenericErrorComponent(subTitle = "Failed to load breeds")
                else -> {
                    if(breedsState.itemCount>0){
                        BreedListComponent(breedsState, onBreedClick)
                    }else{
                        GenericEmptyComponent()
                    }
                }
            }
        } else {
            SearchResultsGridComponent(
                searchState = uiState,
                onBreedClick = onBreedClick
            )
        }
    }

}


@Composable
fun BreedListComponent(
    breeds: LazyPagingItems<BreedUIModel>,
    onBreedClick: (BreedUIModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(Dimen.Images.xLarge),
        contentPadding = PaddingValues(Dimen.Spacing.medium),
        verticalArrangement = Arrangement.spacedBy(Dimen.Spacing.default),
        horizontalArrangement = Arrangement.spacedBy(Dimen.Spacing.default)
    ) {
        items(
            count = breeds.itemCount,
            key = breeds.itemKey { it.id }
        ) { index ->
            val breed = breeds[index]
            breed?.let { breed ->

                BreedListItemComponent(
                    modifier = Modifier
                        .width(Dimen.Images.xLarge)
                        .height(Dimen.Images.xxLarge),
                    id = breed.id,
                    name = breed.name,
                    lifespan = breed.lifespan,
                    imageUrl = breed.imageUrl,
                    onClick = { onBreedClick(breed) },
                )
            }
        }

        if (breeds.loadState.append is LoadState.Loading) {
            item(span = { GridItemSpan(4) }) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun SearchResultsGridComponent(
    searchState: BreedListState,
    onBreedClick: (BreedUIModel) -> Unit,
) {
    when (val state = searchState.searchState) {
        is SearchState.Idle -> {}
        is SearchState.Loading -> GenericLoadingComponent()
        is SearchState.Error -> GenericErrorComponent(subTitle = state.message)
        is SearchState.Success -> {
            if (state.breeds.isEmpty()){
                GenericEmptyComponent()
            }else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(Dimen.Images.xLarge),
                    contentPadding = PaddingValues(Dimen.Spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(Dimen.Spacing.default),
                    horizontalArrangement = Arrangement.spacedBy(Dimen.Spacing.default)
                ) {
                    items(
                        items = state.breeds,
                        key = { it.id }
                    ) { breed ->
                        BreedListItemComponent(
                            modifier = Modifier
                                .width(Dimen.Images.xLarge)
                                .height(Dimen.Images.xxLarge),
                            id = breed.id,
                            name = breed.name,
                            lifespan = breed.lifespan,
                            imageUrl = breed.imageUrl,
                            onClick = { onBreedClick(breed) },
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BreedListScreenContentPreview() {

    val breeds: List<BreedUIModel> = listOf(
        BreedUIModel(
            "aege",
            name = "Aegean",
            imageUrl = "",
            isFavorite = true
        ),
        BreedUIModel(
            "acur",
            name = "American Curl",
            imageUrl = "",
            isFavorite = true
        ),
        BreedUIModel(
            "asdasd",
            name = "test Curl",
            imageUrl = "",
            isFavorite = true
        ),
        BreedUIModel(
            "3432werdf",
            name = "asdgh agean",
            imageUrl = "",
            isFavorite = true
        )
    )
    val pagingItems = flowOf(
        PagingData.from(breeds)
    ).collectAsLazyPagingItems()

    SwordCatChallengeTheme {
        BreedListComponent(pagingItems) {}
    }
}

@Preview(showBackground = true)
@Composable
fun BreedListScreenContentErrorStatePreview() {
    val pagingItems = flowOf(
        PagingData.from(emptyList<BreedUIModel>())
    ).collectAsLazyPagingItems()

    SwordCatChallengeTheme {
        BreedListScreenContent(
            modifier = Modifier,
            uiState = BreedListState().copy(
                searchQuery = "test",
                searchState = SearchState.Error("Timeout")
            ),
            breedsState = pagingItems,
            onSearchQueryChanged = {},
            onBreedClick = { }
        )
    }
}