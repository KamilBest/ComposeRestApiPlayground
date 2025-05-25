package com.best.composeRestApiPlayground.usecase.search

import com.best.composeRestApiPlayground.usecase.search.data.PostModel
import com.best.composeRestApiPlayground.usecase.search.data.PostRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchPostsUseCaseTest {

    private lateinit var useCase: SearchPostsUseCase
    private lateinit var repository: PostRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = SearchPostsUseCaseImpl(repository)
    }

    @Test
    fun `invoke returns posts from repository`() = runTest {
        // Given
        val query = "test"
        val expectedPosts = listOf(
            PostModel(id = 1, title = "Test Post 1", body = "Body 1"),
            PostModel(id = 2, title = "Test Post 2", body = "Body 2")
        )
        coEvery { repository.searchPosts(query) } returns expectedPosts

        // When
        val result = useCase(query)

        // Then
        assertEquals(expectedPosts, result)
    }

    @Test
    fun `invoke returns empty list when repository returns empty list`() = runTest {
        // Given
        val query = "nonexistent"
        coEvery { repository.searchPosts(query) } returns emptyList()

        // When
        val result = useCase(query)

        // Then
        assertEquals(emptyList<PostModel>(), result)
    }

    @Test(expected = RuntimeException::class)
    fun `invoke propagates error from repository`() = runTest {
        // Given
        val query = "test"
        coEvery { repository.searchPosts(query) } throws RuntimeException("Network error")

        // When
        useCase(query)
    }
} 