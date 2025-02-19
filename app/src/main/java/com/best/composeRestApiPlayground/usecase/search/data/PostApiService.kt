package com.best.composeRestApiPlayground.usecase.search.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class PostApiService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun searchPosts(query: String): List<PostModel> {
        return httpClient.get("https://dummyjson.com/posts/search") {
            parameter("q", query)
        }.body<SearchPostResponse>().posts
    }
}