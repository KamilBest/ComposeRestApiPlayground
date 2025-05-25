package com.best.composeRestApiPlayground.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.best.composeRestApiPlayground.usecase.search.SearchPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPostsUseCase: SearchPostsUseCase
) : ViewModel() {

    var uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
        private set

    init {
        observeQueryChanges()
    }

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnQueryChange -> {
                uiState.update { it.copy(query = event.query) }
                if (event.query.isBlank()) {
                    uiState.update { it.copy(results = emptyList(), isLoading = false) }
                }
            }
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeQueryChanges() {
        uiState
            .map { it.query }
            .debounce(DEBOUNCE_MILLIS)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { query ->
                uiState.update { it.copy(isLoading = true) }
            }
            .flatMapLatest { query ->
                flow {
                    val results = searchPostsUseCase(query)
                    emit(results.map { it.toUiModel(query) })
                }.catch {
                    uiState.update { it.copy(results = emptyList(), isLoading = false) }
                }
            }
            .onEach { results ->
                uiState.update { it.copy(results = results, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    companion object {
        private const val DEBOUNCE_MILLIS = 500L
    }
}
