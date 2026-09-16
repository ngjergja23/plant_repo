package org.unizd.rma.gjergja.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import org.unizd.rma.gjergja.data.model.Plant

@Composable
fun PlantListScreen(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    viewModel: PlantListViewModel = viewModel(),
    onPlantClick: (Plant) -> Unit
) {
    val state by viewModel.state.collectAsState() //svaka promjena state-a u ViewModelu automatski pokreće ponovno iscrtavanje ovog dijela ekrana.

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val currentState = state) {
            is PlantListState.Loading -> {
                ShimmerPlantList()
            }

            is PlantListState.Success -> {
                PlantList(
                    plants = currentState.plants,
                    listState = listState,
                    isLoadingMore = currentState.isLoadingMore,
                    onPlantClick = onPlantClick,
                    onLoadMore = { viewModel.loadNextPage() }
                )
            }

            is PlantListState.Error -> {
                Text(text = "Greška: ${currentState.message}")
            }
        }
    }
}

@Composable
fun PlantList(
    plants: List<Plant>,
    listState: LazyListState,
    isLoadingMore: Boolean,
    onPlantClick: (Plant) -> Unit,
    onLoadMore: () -> Unit
){

    val shouldLoadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = layoutInfo.totalItemsCount
            lastVisibleIndex >= totalItems - 5
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !isLoadingMore) {
            onLoadMore()
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        items(plants) { plant ->
            PlantListItem(plant = plant, onClick = { onPlantClick(plant) })
        }

        if (isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
@Composable
fun PlantListItem(plant: Plant, onClick: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = plant.image_url,
                contentDescription = plant.common_name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Text(text = plant.common_name ?: plant.scientific_name ?: "Nepoznata biljka")
                Text(text = plant.scientific_name ?: "")
            }
        }
    }
}