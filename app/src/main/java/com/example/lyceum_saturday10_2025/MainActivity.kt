package com.example.lyceum_saturday10_2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lyceum_saturday10_2025.common.UserPrefsManager
import com.example.lyceum_saturday10_2025.features.NavGraphs
import com.example.lyceum_saturday10_2025.features.auth.presentation.LoginScreen
import com.example.lyceum_saturday10_2025.ui.theme.Lyceum_saturday10_2025Theme
import com.ramcosta.composedestinations.DestinationsNavHost
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.lyceum_saturday10_2025.features.destinations.GithubScreenDestination
import com.example.lyceum_saturday10_2025.features.destinations.GoodsScreenDestination
import com.example.lyceum_saturday10_2025.features.destinations.TodoScreenDestination


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lyceum_saturday10_2025Theme {
                val prefs = remember { UserPrefsManager(applicationContext) }
                var isLoggedIn by remember { mutableStateOf(prefs.accessToken != null) }

                if (!isLoggedIn) {
                    LoginScreen(onLoginSuccess = { isLoggedIn = true })
                } else {
                    val navController = rememberNavController()

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = false,
                                    onClick = { navController.navigate(TodoScreenDestination.route) },
                                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                                    label = { Text("Заметки") }
                                )
                                NavigationBarItem(
                                    selected = false,
                                    onClick = { navController.navigate(GoodsScreenDestination.route) },
                                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
                                    label = { Text("Товары") }
                                )
                                NavigationBarItem(
                                    selected = false,
                                    onClick = { navController.navigate(GithubScreenDestination.route) },
                                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                                    label = { Text("GitHub") }
                                )
                            }
                        }
                    ) { innerPadding ->
                        Column(modifier = Modifier.padding(innerPadding)) {
                            DestinationsNavHost(
                                navGraph = NavGraphs.root,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lyceum_saturday10_2025Theme {
        Greeting("Android")
    }
}