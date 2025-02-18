package com.best.composeRestApiPlayground.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.best.composeRestApiPlayground.ui.screen.MenuScreen
import com.best.composeRestApiPlayground.ui.screen.search.SearchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = MenuScreenDestination) {
        composable<MenuScreenDestination> {
            MenuScreen(onSearchRequestShowcaseClick = {
                navController.navigate(SearchScreenDestination)
            }, onAsyncRequestShowcaseClick = {
              //  navController.navigate("async")
            })
        }
        composable<SearchScreenDestination> {
            SearchScreen()
        }
    }
}