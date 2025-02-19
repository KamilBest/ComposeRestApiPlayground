package com.best.composeRestApiPlayground.ui.screen.search

sealed class SearchUiEvent {
    data class OnQueryChange(val query: String) : SearchUiEvent()
}