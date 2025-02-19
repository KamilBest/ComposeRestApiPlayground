package com.best.composeRestApiPlayground.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.best.composeRestApiPlayground.usecase.search.SearchPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPostsUseCase: SearchPostsUseCase
) : ViewModel() {

    var queryFlow: MutableStateFlow<String> = MutableStateFlow("")
        private set

    var uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
        private set


    init {
        observeQueryChanges()
    }

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnQueryChange -> {
                queryFlow.value = event.query
            }
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeQueryChanges() {
        queryFlow
            .debounce(500)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .flatMapLatest { query ->
                flow {
                    uiState.update { it.copy(isLoading = true) }
                    val results = searchPostsUseCase(query)
                    emit(results.map{it.toUiModel()})
                }
            }
            .catch { e -> e.printStackTrace() }
            .onEach { uiModelsResults ->
                uiState.update {
                    it.copy(results = uiModelsResults, isLoading = false)
                }
            }
            .launchIn(viewModelScope)
    }
}
