package com.best.composeRestApiPlayground.usecase.search.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostModel(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("body") val body: String,
    @SerialName("tags") val tags: List<String>,
    @SerialName("reactions") val reactions: Reactions,
    @SerialName("views") val views: Int,
    @SerialName("userId") val userId: Int
)

@Serializable
data class Reactions(
    @SerialName("likes") val likes: Int,
    @SerialName("dislikes") val dislikes: Int
)
