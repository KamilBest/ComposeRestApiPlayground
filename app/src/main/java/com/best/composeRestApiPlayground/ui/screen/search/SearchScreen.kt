package com.best.composeRestApiPlayground.ui.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.unit.Dp
import com.best.composeRestApiPlayground.usecase.search.PhotoModel

@Composable
fun SearchScreen() {
    var query by remember { mutableStateOf("") }
    val results = remember { sampleData }
    val filteredResults = results.filter { it.title.contains(query, ignoreCase = true) }

    Scaffold(
        topBar = {
            SearchTopBar()
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            SearchBar(query, onQueryChanged = { query = it })

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(filteredResults) { result ->
                    SearchResultItem(result)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar() {
    TopAppBar(
        title = { Text("Search API Test") },
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text("Search...") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions.Default,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SearchResultItem(result: PhotoModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "ID: ${result.id}", style = MaterialTheme.typography.titleMedium)
            Text(text = result.title, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

val sampleData = listOf(
    PhotoModel(
        1,
        1,
        "Lorem Ipsum",
        "https://via.placeholder.com/600/92c952",
        "https://via.placeholder.com/150/92c952"
    ),
    PhotoModel(
        1,
        2,
        "Dolor Sit Amet",
        "https://via.placeholder.com/600/771796",
        "https://via.placeholder.com/150/771796"
    ),
    PhotoModel(
        1,
        4,
        "culpa odio esse rerum omnis laboriosam voluptate repudiandae",
        "https://via.placeholder.com/600/d32776",
        "https://via.placeholder.com/150/d32776"
    )
)