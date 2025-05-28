package com.best.composeRestApiPlayground.usecase.async

import com.best.composeRestApiPlayground.usecase.async.data.AsyncRepository
import com.best.composeRestApiPlayground.usecase.async.data.CommentModel
import com.best.composeRestApiPlayground.usecase.async.data.RecipeModel
import com.best.composeRestApiPlayground.usecase.search.data.PostModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

data class AsyncRequestsResult(
    val recipe: RecipeModel,
    val comment: CommentModel,
    val post: PostModel
)

interface AsyncRequestsUseCase {
    suspend operator fun invoke(): AsyncRequestsResult
}

class AsyncRequestsUseCaseImpl @Inject constructor(
    private val repository: AsyncRepository
) : AsyncRequestsUseCase {
    override suspend operator fun invoke(): AsyncRequestsResult = coroutineScope {
        val recipesDeferred = async { repository.getRecipe(1) }
        val commentsDeferred = async { repository.getComment(1) }
        val postDeferred = async { repository.getPost(1) }

        AsyncRequestsResult(
            recipe = recipesDeferred.await(),
            comment = commentsDeferred.await(),
            post = postDeferred.await()
        )
    }
} 