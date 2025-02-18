package com.best.composeRestApiPlayground.usecase.search

data class PhotoModel(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)