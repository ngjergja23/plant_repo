package org.unizd.rma.gjergja.data.api

import org.unizd.rma.gjergja.data.model.PlantResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlantApiService {
    @GET("api/v1/plants")
    suspend fun getPlants(
    @Query("token")token: String,
    @Query("page") page: Int = 1
    ): PlantResponse
}