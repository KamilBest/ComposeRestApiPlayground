package com.best.composeRestApiPlayground.ui.screen.async

import app.cash.turbine.test
import com.best.composeRestApiPlayground.usecase.async.AsyncRequestsResult
import com.best.composeRestApiPlayground.usecase.async.AsyncRequestsUseCase
import com.best.composeRestApiPlayground.usecase.async.data.CommentModel
import com.best.composeRestApiPlayground.usecase.async.data.RecipeModel
import com.best.composeRestApiPlayground.usecase.search.data.PostModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AsyncViewModelTest {

    private lateinit var viewModel: AsyncViewModel
    private lateinit var useCase: AsyncRequestsUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        useCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading`() = runTest {
        // Given
        viewModel = AsyncViewModel(useCase)

        // Then
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertEquals(true, initialState.isLoading)
            assertEquals(null, initialState.error)
            assertEquals(null, initialState.recipe)
            assertEquals(null, initialState.comment)
            assertEquals(null, initialState.post)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadData updates state with data when successful`() = runTest {
        // Given
        val recipe = RecipeModel(
            name = "Test Recipe",
            ingredients = listOf("ingredient1", "ingredient2"),
            instructions = listOf("step1", "step2")
        )
        val comment = CommentModel(
            id = 1,
            body = "Test comment"
        )
        val post = PostModel(
            id = 1,
            title = "Test Post",
            body = "Test body"
        )
        val result = AsyncRequestsResult(recipe, comment, post)
        coEvery { useCase() } returns result

        viewModel = AsyncViewModel(useCase)

        // Then
        viewModel.uiState.test {
            // Initial state
            val initialState = awaitItem()
            assertEquals(true, initialState.isLoading)

            // Final state
            val finalState = awaitItem()
            assertEquals(false, finalState.isLoading)
            assertEquals(null, finalState.error)
            assertEquals(recipe, finalState.recipe)
            assertEquals(comment, finalState.comment)
            assertEquals(post, finalState.post)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadData updates state with error when request fails`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { useCase() } throws RuntimeException(errorMessage)

        viewModel = AsyncViewModel(useCase)

        // Then
        viewModel.uiState.test {
            // Initial state
            val initialState = awaitItem()
            assertEquals(true, initialState.isLoading)

            // Error state
            val errorState = awaitItem()
            assertEquals(false, errorState.isLoading)
            assertEquals(errorMessage, errorState.error)
            assertEquals(null, errorState.recipe)
            assertEquals(null, errorState.comment)
            assertEquals(null, errorState.post)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadData resets error state when retrying`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { useCase() } throws RuntimeException(errorMessage)

        viewModel = AsyncViewModel(useCase)

        // Then
        viewModel.uiState.test {
            // Initial state
            val initialState = awaitItem()
            assertEquals(true, initialState.isLoading)

            // Error state
            val errorState = awaitItem()
            assertEquals(false, errorState.isLoading)
            assertEquals(errorMessage, errorState.error)

            // When retrying
            val retryRecipe = RecipeModel(
                name = "Retry Recipe",
                ingredients = listOf("retry1", "retry2"),
                instructions = listOf("retry step1", "retry step2")
            )
            val retryComment = CommentModel(
                id = 2,
                body = "Retry comment"
            )
            val retryPost = PostModel(
                id = 2,
                title = "Retry Post",
                body = "Retry body"
            )
            val retryResult = AsyncRequestsResult(retryRecipe, retryComment, retryPost)
            coEvery { useCase() } returns retryResult
            viewModel.loadData()
            testDispatcher.scheduler.advanceUntilIdle()

            // Loading state during retry
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)
            assertEquals(null, loadingState.error)

            // Success state after retry
            val successState = awaitItem()
            assertEquals(false, successState.isLoading)
            assertEquals(null, successState.error)
            assertEquals(retryRecipe, successState.recipe)
            assertEquals(retryComment, successState.comment)
            assertEquals(retryPost, successState.post)
            cancelAndIgnoreRemainingEvents()
        }
    }
} 