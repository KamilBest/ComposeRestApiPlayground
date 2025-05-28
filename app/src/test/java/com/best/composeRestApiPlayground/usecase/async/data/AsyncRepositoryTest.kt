package com.best.composeRestApiPlayground.usecase.async.data

import com.best.composeRestApiPlayground.usecase.search.data.PostModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AsyncRepositoryTest {

    private lateinit var repository: AsyncRepository
    private lateinit var apiService: AsyncApiService

    @Before
    fun setup() {
        apiService = mockk()
        repository = AsyncRepositoryImpl(apiService)
    }

    @Test
    fun `getRecipe returns recipe from api service`() = runTest {
        // Given
        val expectedRecipe = RecipeModel(
            name = "Test Recipe",
            ingredients = listOf("ingredient1", "ingredient2"),
            instructions = listOf("step1", "step2")
        )
        coEvery { apiService.getRecipe(1) } returns expectedRecipe

        // When
        val result = repository.getRecipe(1)

        // Then
        assertEquals(expectedRecipe, result)
    }

    @Test
    fun `getComment returns comment from api service`() = runTest {
        // Given
        val expectedComment = CommentModel(
            id = 1,
            body = "Test comment",
        )
        coEvery { apiService.getComment(1) } returns expectedComment

        // When
        val result = repository.getComment(1)

        // Then
        assertEquals(expectedComment, result)
    }

    @Test
    fun `getPost returns post from api service`() = runTest {
        // Given
        val expectedPost = PostModel(
            id = 1,
            title = "Test Post",
            body = "Test body"
        )
        coEvery { apiService.getPost(1) } returns expectedPost

        // When
        val result = repository.getPost(1)

        // Then
        assertEquals(expectedPost, result)
    }

    @Test(expected = RuntimeException::class)
    fun `getRecipe propagates error from api service`() = runTest {
        // Given
        coEvery { apiService.getRecipe(1) } throws RuntimeException("Network error")

        // When
        repository.getRecipe(1)
    }

    @Test(expected = RuntimeException::class)
    fun `getComment propagates error from api service`() = runTest {
        // Given
        coEvery { apiService.getComment(1) } throws RuntimeException("Network error")

        // When
        repository.getComment(1)
    }

    @Test(expected = RuntimeException::class)
    fun `getPost propagates error from api service`() = runTest {
        // Given
        coEvery { apiService.getPost(1) } throws RuntimeException("Network error")

        // When
        repository.getPost(1)
    }
} 