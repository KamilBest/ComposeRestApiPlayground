package com.best.composeRestApiPlayground.usecase.async.data

import com.best.composeRestApiPlayground.usecase.search.data.PostModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.delay
import javax.inject.Inject

class AsyncApiService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getRecipe(id: Int): RecipeModel {
        // Add delay to test loading state
        delay(3000)
        return httpClient.get("$ENDPOINT_RECIPES/$id").body<RecipeModel>()
    }

    suspend fun getComment(id: Int): CommentModel {
        return httpClient.get("$ENDPOINT_COMMENTS/$id").body<CommentModel>()
    }

    suspend fun getPost(id: Int): PostModel {
        return httpClient.get("$ENDPOINT_POSTS/$id").body<PostModel>()
    }

    private companion object {
        const val ENDPOINT_RECIPES = "https://dummyjson.com/recipes"
        const val ENDPOINT_COMMENTS = "https://dummyjson.com/comments"
        const val ENDPOINT_POSTS = "https://dummyjson.com/posts"
    }
} 