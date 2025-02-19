package com.best.composeRestApiPlayground.ui.screen.search

import com.best.composeRestApiPlayground.usecase.search.data.PostModel

data class PostUiModel(
    val title: String,
    val body: String,
)

fun PostModel.toUiModel(): PostUiModel {
    return PostUiModel(
        title = this.title,
        body = this.body,
    )
}