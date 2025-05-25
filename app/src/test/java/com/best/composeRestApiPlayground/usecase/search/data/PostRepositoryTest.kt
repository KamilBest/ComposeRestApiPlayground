package com.best.composeRestApiPlayground.usecase.search.data

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PostRepositoryTest {

    private lateinit var repository: PostRepository
    private lateinit var apiService: PostApiService

    @Before
    fun setup() {
        apiService = mockk()
        repository = PostRepositoryImpl(apiService)
    }

    @Test
    fun `searchPosts returns posts from api service`() = runTest {
        // Given
        val query = "test"
        val expectedPosts = listOf(
            PostModel(id = 1, title = "Test Post 1", body = "Body 1"),
            PostModel(id = 2, title = "Test Post 2", body = "Body 2")
        )
        coEvery { apiService.searchPostsByTitle(query) } returns expectedPosts

        // When
        val result = repository.searchPosts(query)

        // Then
        assertEquals(expectedPosts, result)
    }

    @Test
    fun `searchPosts returns empty list when api service returns empty list`() = runTest {
        // Given
        val query = "nonexistent"
        coEvery { apiService.searchPostsByTitle(query) } returns emptyList()

        // When
        val result = repository.searchPosts(query)

        // Then
        assertEquals(emptyList<PostModel>(), result)
    }

    @Test(expected = RuntimeException::class)
    fun `searchPosts propagates error from api service`() = runTest {
        // Given
        val query = "test"
        coEvery { apiService.searchPostsByTitle(query) } throws RuntimeException("Network error")

        // When
        repository.searchPosts(query)
    }
} 