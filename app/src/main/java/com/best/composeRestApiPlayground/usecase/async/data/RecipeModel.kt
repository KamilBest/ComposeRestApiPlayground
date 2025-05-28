package com.best.composeRestApiPlayground.usecase.async.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeModel(
    @SerialName("name") val name: String,
    @SerialName("ingredients") val ingredients: List<String>,
    @SerialName("instructions") val instructions: List<String>
) 