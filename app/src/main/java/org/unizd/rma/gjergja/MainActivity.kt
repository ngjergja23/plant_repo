package org.unizd.rma.gjergja

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.unizd.rma.gjergja.data.model.Plant
import org.unizd.rma.gjergja.ui.detail.PlantDetailScreen
import org.unizd.rma.gjergja.ui.list.PlantListScreen
import org.unizd.rma.gjergja.ui.theme.PlantExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlantExplorerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PlantExplorerApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PlantExplorerApp(modifier: Modifier = Modifier) {
    var selectedPlant by remember { mutableStateOf<Plant?>(null) }
    val listState = rememberLazyListState()

    BackHandler(enabled = selectedPlant != null) {
        selectedPlant = null
    }

    if (selectedPlant == null) {
        PlantListScreen(
            modifier = modifier,
            listState = listState,
            onPlantClick = { plant -> selectedPlant = plant }
        )
    } else {
        PlantDetailScreen(
            plant = selectedPlant!!,
            modifier = modifier,
            onBackClick = { selectedPlant = null}
        )
    }

}