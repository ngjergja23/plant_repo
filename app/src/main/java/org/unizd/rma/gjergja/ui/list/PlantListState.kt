package org.unizd.rma.gjergja.ui.list

import org.unizd.rma.gjergja.data.model.Plant

sealed class PlantListState {
    object Loading : PlantListState() // object (singleton), ne class jer je uvijek isti
    data class Success(
        val plants: List<Plant>,
        val currentPage: Int = 1,
        val isLoadingMore: Boolean = false
    ) : PlantListState()
    data class Error(val message: String) : PlantListState()
}