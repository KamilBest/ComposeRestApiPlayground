package com.best.composeRestApiPlayground.usecase.search.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostModel(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("body") val body: String? = "",
    @SerialName("tags") val tags: List<String> = emptyList(),
    @SerialName("reactions") val reactions: Reactions? = null,
    @SerialName("views") val views: Int = 0,
    @SerialName("userId") val userId: Int = 0
)

@Serializable
data class Reactions(
    @SerialName("likes") val likes: Int = 0,
    @SerialName("dislikes") val dislikes: Int = 0
)