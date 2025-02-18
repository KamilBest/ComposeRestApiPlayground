package com.best.composeRestApiPlayground.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.best.composeRestApiPlayground.ui.theme.ComposeRestApiPlaygroundTheme

@Composable
fun MenuScreen(onSearchRequestShowcaseClick: () -> Unit, onAsyncRequestShowcaseClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            20.dp,
            Alignment.CenterVertically
        )
    ) {
        Button(contentPadding = PaddingValues(30.dp), onClick = onSearchRequestShowcaseClick) {
            Text(fontSize = 20.sp, text = "Search request")
        }
        Button(contentPadding = PaddingValues(30.dp), onClick = onAsyncRequestShowcaseClick) {
            Text(fontSize = 20.sp, text = "Async requests")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    ComposeRestApiPlaygroundTheme {
       // MenuScreen()
    }
}