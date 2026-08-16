package org.unizd.rma.gjergja.data.model

data class PlantResponse(
    val data: List<Plant>
)

data class Plant(
    val id: Int,
    val common_name: String?,
    val slug: String,
    val scientific_name: String?,
    val family: String?,
    val genus: String?,
    val image_url: String?,
    val year: Int?,
    val author: String?

)