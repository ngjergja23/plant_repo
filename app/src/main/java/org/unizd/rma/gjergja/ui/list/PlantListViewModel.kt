package org.unizd.rma.gjergja.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.unizd.rma.gjergja.BuildConfig
import org.unizd.rma.gjergja.data.api.RetrofitInstance

class PlantListViewModel : ViewModel() {
    private val _state = MutableStateFlow<PlantListState>(PlantListState.Loading)
    val state: StateFlow<PlantListState> = _state

    init {
        loadPlants(page = 1)
    }

    private fun loadPlants(page: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getPlants(BuildConfig.TREFLE_TOKEN, page = page)
                val existingPlants = (_state.value as? PlantListState.Success)?.plants ?: emptyList() // cast _state.value u konkretan Success tip da bismo uopce imali pristup .plants polju
                _state.value = PlantListState.Success(
                    plants = existingPlants + response.data,
                    currentPage = page
                )
            } catch (e: Exception) {
                _state.value = PlantListState.Error(e.message ?: "Greška pri dohvaćanju")
            }
        }
    }

    fun loadNextPage() {
        val current = state.value as? PlantListState.Success ?: return
        _state.value = current.copy(isLoadingMore = true)
        loadPlants(page = current.currentPage + 1)
    }
}