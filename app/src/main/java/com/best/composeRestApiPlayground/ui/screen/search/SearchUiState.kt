package com.best.composeRestApiPlayground.ui.screen.search

data class SearchUiState(
    val results: List<PostUiModel> = emptyList(),
    val isLoading: Boolean = false
)