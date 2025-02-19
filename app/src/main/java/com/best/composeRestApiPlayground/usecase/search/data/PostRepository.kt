package com.best.composeRestApiPlayground.usecase.search.data

import javax.inject.Inject

interface PostRepository {
    suspend fun searchPosts(query: String): List<PostModel>
}

class PostRepositoryImpl @Inject constructor(
    private val apiService: PostApiService
): PostRepository {
    override suspend fun searchPosts(query: String): List<PostModel> {
        return apiService.searchPosts(query)
    }
}