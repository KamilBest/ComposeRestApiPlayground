package com.best.composeRestApiPlayground.usecase.search.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchPostResponse(
    @SerialName("posts") val posts: List<PostModel>
)
