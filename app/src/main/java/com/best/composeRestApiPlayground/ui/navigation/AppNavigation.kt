package com.best.composeRestApiPlayground.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.best.composeRestApiPlayground.ui.screen.MenuScreen
import com.best.composeRestApiPlayground.ui.screen.async.AsyncRequestsScreen
import com.best.composeRestApiPlayground.ui.screen.search.SearchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = MenuScreenDestination) {
        composable<MenuScreenDestination> {
            MenuScreen(onSearchRequestShowcaseClick = {
                navController.navigate(SearchScreenDestination)
            }, onAsyncRequestShowcaseClick = {
                navController.navigate(AsyncRequestsScreenDestination)
            })
        }
        composable<SearchScreenDestination> {
            SearchScreen()
        }
        composable<AsyncRequestsScreenDestination> {
            AsyncRequestsScreen()
        }
    }
}