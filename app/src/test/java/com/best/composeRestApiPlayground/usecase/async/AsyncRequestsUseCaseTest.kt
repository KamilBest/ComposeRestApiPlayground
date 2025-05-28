package com.best.composeRestApiPlayground.usecase.async

import com.best.composeRestApiPlayground.usecase.async.data.AsyncRepository
import com.best.composeRestApiPlayground.usecase.async.data.CommentModel
import com.best.composeRestApiPlayground.usecase.async.data.RecipeModel
import com.best.composeRestApiPlayground.usecase.search.data.PostModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AsyncRequestsUseCaseTest {

    private lateinit var useCase: AsyncRequestsUseCase
    private lateinit var repository: AsyncRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = AsyncRequestsUseCaseImpl(repository)
    }

    @Test
    fun `invoke returns combined result when all requests succeed`() = runTest {
        // Given
        val recipe = RecipeModel(
            name = "Test Recipe",
            ingredients = listOf("ingredient1", "ingredient2"),
            instructions = listOf("step1", "step2")
        )
        val comment = CommentModel(
            id = 1,
            body = "Test comment",
        )
        val post = PostModel(
            id = 1,
            title = "Test Post",
            body = "Test body"
        )

        coEvery { repository.getRecipe(1) } returns recipe
        coEvery { repository.getComment(1) } returns comment
        coEvery { repository.getPost(1) } returns post

        // When
        val result = useCase()

        // Then
        assertEquals(recipe, result.recipe)
        assertEquals(comment, result.comment)
        assertEquals(post, result.post)
    }

    @Test(expected = RuntimeException::class)
    fun `invoke throws exception when recipe request fails`() = runTest {
        // Given
        coEvery { repository.getRecipe(1) } throws RuntimeException("Recipe error")
        coEvery { repository.getComment(1) } returns mockk()
        coEvery { repository.getPost(1) } returns mockk()

        // When
        useCase()
    }

    @Test(expected = RuntimeException::class)
    fun `invoke throws exception when comment request fails`() = runTest {
        // Given
        coEvery { repository.getRecipe(1) } returns mockk()
        coEvery { repository.getComment(1) } throws RuntimeException("Comment error")
        coEvery { repository.getPost(1) } returns mockk()

        // When
        useCase()
    }

    @Test(expected = RuntimeException::class)
    fun `invoke throws exception when post request fails`() = runTest {
        // Given
        coEvery { repository.getRecipe(1) } returns mockk()
        coEvery { repository.getComment(1) } returns mockk()
        coEvery { repository.getPost(1) } throws RuntimeException("Post error")

        // When
        useCase()
    }
} 