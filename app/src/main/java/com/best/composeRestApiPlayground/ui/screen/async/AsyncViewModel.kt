package com.best.composeRestApiPlayground.ui.screen.async

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.best.composeRestApiPlayground.usecase.async.AsyncRequestsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsyncViewModel @Inject constructor(
    private val asyncRequestsUseCase: AsyncRequestsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AsyncUiState())
    val uiState: StateFlow<AsyncUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val result = asyncRequestsUseCase()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        recipe = result.recipe,
                        comment = result.comment,
                        post = result.post
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An error occurred"
                    )
                }
            }
        }
    }
} 