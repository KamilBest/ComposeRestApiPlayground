package com.best.composeRestApiPlayground.ui.screen.async

import com.best.composeRestApiPlayground.usecase.async.data.CommentModel
import com.best.composeRestApiPlayground.usecase.async.data.RecipeModel
import com.best.composeRestApiPlayground.usecase.search.data.PostModel

data class AsyncUiState(
    val isLoading: Boolean = false,
    val recipe: RecipeModel? = null,
    val comment: CommentModel? = null,
    val post: PostModel? = null,
    val error: String? = null
) 