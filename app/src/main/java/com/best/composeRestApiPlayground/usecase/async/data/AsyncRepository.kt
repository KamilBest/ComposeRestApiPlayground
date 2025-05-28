package com.best.composeRestApiPlayground.usecase.async.data

import com.best.composeRestApiPlayground.usecase.search.data.PostModel
import javax.inject.Inject

interface AsyncRepository {
    suspend fun getRecipe(id: Int): RecipeModel
    suspend fun getComment(id: Int): CommentModel
    suspend fun getPost(id: Int): PostModel
}

class AsyncRepositoryImpl @Inject constructor(
    private val apiService: AsyncApiService
) : AsyncRepository {
    override suspend fun getRecipe(id: Int): RecipeModel {
        return apiService.getRecipe(id)
    }

    override suspend fun getComment(id: Int): CommentModel {
        return apiService.getComment(id)
    }

    override suspend fun getPost(id: Int): PostModel {
        return apiService.getPost(id)
    }
} 