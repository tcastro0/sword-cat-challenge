package com.tcastro.feature.breeds.list.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text("Cats") })
        },
        content = { paddingValues ->
            BreedListScreenContent(
                paddingValues,
                uiState,
                breedsState,
                viewModel::onSearchQueryChanged,
                viewModel::onBreedClick
            )

        }
    )
}

@Composable
fun BreedListScreenContent(
    paddingValues: PaddingValues,
    uiState: BreedListState,
    breedsState: LazyPagingItems<BreedUIModel>,
    onSearchQueryChanged: (String) -> Unit,
    onBreedClick: (BreedUIModel) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearchQueryChanged,
            placeholder = { Text("Search ...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            singleLine = true,
            shape = RoundedCornerShape(24.dp)
        )


        if (uiState.searchQuery.isEmpty()) {
            when (breedsState.loadState.refresh) {
                is LoadState.Loading -> CircularProgressIndicator()
                is LoadState.Error -> Text("Failed to load breeds")
                else -> {
                    BreedListComponent(breedsState, onBreedClick)
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
        columns = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = breeds.itemCount,
            key = breeds.itemKey { it.id }
        ) { index ->
            val breed = breeds[index]
            breed?.let { breed ->
                Card(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable { onBreedClick(breed) }
                ) {
                    Text(breed.name)
                }
            }
        }

        if (breeds.loadState.append is LoadState.Loading) {
            item(span = { GridItemSpan(4) }) {
                Spacer(Modifier.height(50.dp))
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
        is SearchState.Loading -> CircularProgressIndicator()
        is SearchState.Error -> Text(state.message)
        is SearchState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = state.breeds,
                    key = { it.id }
                ) { breed ->
                    Card(
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .clickable { onBreedClick(breed) }
                    ) {
                        Text(breed.name)
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

    BreedListComponent(pagingItems,{})
}