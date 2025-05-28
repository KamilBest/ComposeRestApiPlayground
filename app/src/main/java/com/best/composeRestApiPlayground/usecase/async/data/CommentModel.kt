package com.best.composeRestApiPlayground.usecase.async.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentModel(
    @SerialName("id") val id: Int,
    @SerialName("body") val body: String
)