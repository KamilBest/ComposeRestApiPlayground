package com.best.composeRestApiPlayground.usecase.search.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class PostApiService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun searchPostsByTitle(query: String): List<PostModel> {
        return httpClient.get(ENDPOINT_SEARCH) {
            parameter(PARAM_QUERY, query)
        }.body<SearchPostResponse>().posts
    }

    private companion object {
        const val ENDPOINT_SEARCH = "https://dummyjson.com/posts/search"
        const val PARAM_QUERY = "q"
    }
}