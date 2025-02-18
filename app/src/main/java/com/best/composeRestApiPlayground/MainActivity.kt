package com.best.composeRestApiPlayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.best.composeRestApiPlayground.ui.navigation.AppNavigation
import com.best.composeRestApiPlayground.ui.theme.ComposeRestApiPlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeRestApiPlaygroundTheme {
                AppNavigation()
            }
        }
    }
}