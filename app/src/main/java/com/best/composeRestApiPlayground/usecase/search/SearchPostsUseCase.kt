package com.best.composeRestApiPlayground.usecase.search

import com.best.composeRestApiPlayground.usecase.search.data.PostModel
import com.best.composeRestApiPlayground.usecase.search.data.PostRepository
import javax.inject.Inject

interface SearchPostsUseCase {
    suspend operator fun invoke(query: String): List<PostModel>
}

class SearchPostsUseCaseImpl @Inject constructor(
    private val repository: PostRepository
): SearchPostsUseCase {
    override suspend operator fun invoke(query: String): List<PostModel> {
        return repository.searchPosts(query = query)
    }
}