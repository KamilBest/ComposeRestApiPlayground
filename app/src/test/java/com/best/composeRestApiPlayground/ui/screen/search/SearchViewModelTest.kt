package com.best.composeRestApiPlayground.ui.screen.search

import app.cash.turbine.test
import com.best.composeRestApiPlayground.usecase.search.SearchPostsUseCase
import com.best.composeRestApiPlayground.usecase.search.data.PostModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var searchPostsUseCase: SearchPostsUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        searchPostsUseCase = mockk()
        viewModel = SearchViewModel(searchPostsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when query is empty, results should be empty`() = runTest {
        viewModel.uiState.test {
            // Initial state
            assertEquals(SearchUiState(), awaitItem())

            // Trigger no-op update
            viewModel.onEvent(SearchUiEvent.OnQueryChange(""))

            // Fast-forward (only needed if debounce > 0)
            advanceUntilIdle()

            // Expect no emission since state didn't change
            expectNoEvents()
        }
    }

    @Test
    fun `when query is not empty, should show loading and then results`() =
        runTest(testDispatcher) {
            val testQuery = "test"
            val testPosts = listOf(
                PostModel(id = 1, title = "Test Post 1", body = "Body 1"),
                PostModel(id = 2, title = "Test Post 2", body = "Body 2")
            )

            coEvery { searchPostsUseCase(testQuery) } returns testPosts

            viewModel.uiState.test {
                // Initial state
                assertEquals(SearchUiState(), awaitItem())

                // Update query
                viewModel.onEvent(SearchUiEvent.OnQueryChange(testQuery))

                // First state update - query change
                assertEquals(
                    SearchUiState(
                        query = testQuery,
                        results = emptyList(),
                        isLoading = false
                    ), awaitItem()
                )

                // Advance time to trigger debounce
                testDispatcher.scheduler.advanceTimeBy(500L)
                testDispatcher.scheduler.advanceUntilIdle()

                // Should show loading
                assertEquals(
                    SearchUiState(
                        query = testQuery,
                        results = emptyList(),
                        isLoading = true
                    ), awaitItem()
                )

                // Should show results
                val expectedResults = testPosts.map { it.toUiModel(testQuery) }
                assertEquals(
                    SearchUiState(query = testQuery, results = expectedResults, isLoading = false),
                    awaitItem()
                )
            }
        }

    @Test
    fun `when search fails, should handle error gracefully`() = runTest(testDispatcher) {
        val testQuery = "test"
        coEvery { searchPostsUseCase(testQuery) } throws RuntimeException("Network error")

        viewModel.uiState.test {
            // Initial state
            assertEquals(SearchUiState(), awaitItem())

            // Update query
            viewModel.onEvent(SearchUiEvent.OnQueryChange(testQuery))

            // First state update - query change
            assertEquals(
                SearchUiState(query = testQuery, results = emptyList(), isLoading = false),
                awaitItem()
            )

            // Advance time to trigger debounce
            testDispatcher.scheduler.advanceTimeBy(500L)
            testDispatcher.scheduler.advanceUntilIdle()

            // Should show loading
            assertEquals(
                SearchUiState(query = testQuery, results = emptyList(), isLoading = true),
                awaitItem()
            )

            // Should show empty results after error
            assertEquals(
                SearchUiState(query = testQuery, results = emptyList(), isLoading = false),
                awaitItem()
            )
        }
    }

    @Test
    fun `when query changes rapidly, only last query should trigger search`() =
        runTest(testDispatcher) {
            val finalQuery = "final"
            val testPosts = listOf(
                PostModel(id = 1, title = "Test Post 1", body = "Body 1")
            )

            coEvery { searchPostsUseCase(finalQuery) } returns testPosts

            viewModel.uiState.test {
                // Initial state
                assertEquals(SearchUiState(), awaitItem())

                // Rapid query changes
                viewModel.onEvent(SearchUiEvent.OnQueryChange("first"))
                assertEquals(
                    SearchUiState(
                        query = "first",
                        results = emptyList(),
                        isLoading = false
                    ), awaitItem()
                )

                viewModel.onEvent(SearchUiEvent.OnQueryChange("second"))
                assertEquals(
                    SearchUiState(
                        query = "second",
                        results = emptyList(),
                        isLoading = false
                    ), awaitItem()
                )

                viewModel.onEvent(SearchUiEvent.OnQueryChange(finalQuery))
                assertEquals(
                    SearchUiState(
                        query = finalQuery,
                        results = emptyList(),
                        isLoading = false
                    ), awaitItem()
                )

                // Advance time to trigger debounce for the last query
                testDispatcher.scheduler.advanceTimeBy(500L)
                testDispatcher.scheduler.advanceUntilIdle()

                // Should show loading
                assertEquals(
                    SearchUiState(
                        query = finalQuery,
                        results = emptyList(),
                        isLoading = true
                    ), awaitItem()
                )

                // Should show results for the final query
                val expectedResults = testPosts.map { it.toUiModel(finalQuery) }
                assertEquals(
                    SearchUiState(query = finalQuery, results = expectedResults, isLoading = false),
                    awaitItem()
                )
            }
        }

    @Test
    fun `when identical query is entered, should not trigger new search`() =
        runTest(testDispatcher) {
            val testQuery = "test"
            val testPosts = listOf(
                PostModel(id = 1, title = "Test Post 1", body = "Body 1")
            )

            coEvery { searchPostsUseCase(testQuery) } returns testPosts

            viewModel.uiState.test {
                assertEquals(SearchUiState(), awaitItem())

                // First search
                viewModel.onEvent(SearchUiEvent.OnQueryChange(testQuery))
                assertEquals(
                    SearchUiState(
                        query = testQuery,
                        results = emptyList(),
                        isLoading = false
                    ), awaitItem()
                )

                testDispatcher.scheduler.advanceTimeBy(500L)
                testDispatcher.scheduler.advanceUntilIdle()

                assertEquals(
                    SearchUiState(
                        query = testQuery,
                        results = emptyList(),
                        isLoading = true
                    ), awaitItem()
                )

                val expectedResults = testPosts.map { it.toUiModel(testQuery) }
                assertEquals(
                    SearchUiState(query = testQuery, results = expectedResults, isLoading = false),
                    awaitItem()
                )

                // Enter same query again - should not trigger new search
                viewModel.onEvent(SearchUiEvent.OnQueryChange(testQuery))
                testDispatcher.scheduler.advanceTimeBy(500L)
                testDispatcher.scheduler.advanceUntilIdle()

                coVerify(exactly = 1) { searchPostsUseCase(testQuery) }
            }
        }
}