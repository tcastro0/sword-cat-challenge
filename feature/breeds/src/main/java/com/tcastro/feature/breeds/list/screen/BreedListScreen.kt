package com.tcastro.feature.breeds.list.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tcastro.feature.breeds.list.model.BreedUIModel
import com.tcastro.feature.breeds.list.state.BreedListState
import com.tcastro.feature.breeds.list.viewmodel.BreedListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedListScreen(
    modifier: Modifier = Modifier,
    viewModel: BreedListViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text("Cats") })
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                when (val immutableState = uiState) {
                    is BreedListState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is BreedListState.Error -> {
                        //TODO error state
                        Text(immutableState.message)
                    }

                    is BreedListState.Success -> {
                        //TODO empty state
                        BreedListScreenContent(immutableState.breeds)
                    }

                }
            }
        }
    )
}

@Composable
fun BreedListScreenContent(breeds: List<BreedUIModel>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = breeds,
            key = { it.id }
        ) { breed ->

            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            ) {
                Text(breed.name)

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
        )        ,
        BreedUIModel(
            "asdasd",
            name = "test Curl",
            imageUrl = "",
            isFavorite = true
        )
        ,
        BreedUIModel(
            "3432werdf",
            name = "asdgh agean",
            imageUrl = "",
            isFavorite = true
        )
    )
    BreedListScreenContent(breeds)
}