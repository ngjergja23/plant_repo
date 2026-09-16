package org.unizd.rma.gjergja.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.unizd.rma.gjergja.data.model.Plant

@Composable
fun PlantDetailScreen(
    plant: Plant,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
    ){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = onBackClick) {
            Text("Natrag")
        }
        AsyncImage(
            model = plant.image_url,
            contentDescription = plant.common_name,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = plant.common_name ?: plant.scientific_name ?: "Nepoznata biljka",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
            )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                DetailRow(label = "Znanstveni naziv", value = plant.scientific_name)
                DetailRow(label = "Porodica", value = plant.family)
                DetailRow(label = "Rod", value = plant.genus)
                DetailRow(label = "Godina opisa", value = plant.year?.toString())
                DetailRow(label = "Autor", value = plant.author)
            }
        }

    }
}

@Composable
fun DetailRow(label: String, value: String?) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value ?: "Nepoznato",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}